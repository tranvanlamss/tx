package com.vietsoft.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;


public class ExcelWorkbook {
    static final Logger logger = LoggerFactory.getLogger(ExcelWorkbook.class);
    static final String tempDir = System.getProperty("java.io.tmpdir");
    
    static final SimpleDateFormat excelSufix = new SimpleDateFormat("yyyyMMddHHmm'.xlsx'");
    
    XSSFWorkbook workbook;
    File copyingFile = null; 

    Hashtable<String, CellStyle> workbookStyles = new Hashtable<String, CellStyle>();
    
//    public ExcelWorkbook(String templateFile) {
//    	try {
//			workbook = getTemplateExcel(Constants.RESOURCES_EXCELS_PATH +"en/", templateFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
    
    public ExcelWorkbook(String templateFile, String prefixExcelName, HttpServletRequest request, HttpServletResponse response) {
    	String lang = TokenUtil.getCookieValue(request, "lang");
    	if (StringUtils.isEmpty(lang)) {
    		lang = "en/";
    	} else {
    		lang += "/";
    	}
    	try {
			workbook = getTemplateExcel(Constants.RESOURCES_EXCELS_PATH + lang, templateFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	String excelFileName = "";
    	
    	if (prefixExcelName.startsWith("BDY_") || prefixExcelName.startsWith("CTN_")) {
    		excelFileName += prefixExcelName;
    	} else {
    		if (lang.equals("vi/")) {
    			excelFileName += prefixExcelName;
        	} else {
        		excelFileName += templateFile.replace("template_", "").replace(".xlsx", "");
        	}
    	}

    	excelFileName += excelSufix.format(new Date());
    	
    	try {
			setDownloadExcelFile(response, excelFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public XSSFWorkbook getWorkbook() {
    	return workbook;
    }
    public XSSFSheet getSheet() {
    	return workbook.getSheetAt(0);
    }
    public XSSFSheet getSheetAt(int idx) {
    	return workbook.getSheetAt(idx);
    }
    
    public void write(OutputStream out) {
    	try {
			workbook.write(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (Exception e) {}
			try {
				out.close();
			} catch (Exception e) {} 
		}
    	if (copyingFile != null && copyingFile.isFile()) {
    		try {
    			copyingFile.delete();
			} catch (Exception e) {} 
    	}
    }
    
	public synchronized void addStyle(String identifier, CellStyle style) {
		workbookStyles.put(identifier, style);
	}
	public synchronized CellStyle getStyle(String identifier) {
		return (workbookStyles.get(identifier));
	}
	
	public void formatCell(Cell cell, String pattern) {
		CellStyle cellStyle = this.getStyle(pattern);
		if (cellStyle == null) {
			cellStyle = workbook.createCellStyle();
			CreationHelper createHelper = workbook.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(pattern));
			addStyle(pattern, cellStyle);
		}
		cell.setCellStyle(cellStyle);
	}
    	
    public void autoSizeColumns(int length, int startColumn){
        for (int i = 0; i< length; i++) {
        	getSheet().autoSizeColumn(startColumn + i);
        }
    }
    
    public XSSFWorkbook getTemplateExcel(String path, String fileName) throws IOException {
        String randFilename = "traxem.export." + UUID.randomUUID().toString() + ".xlsx";
        Path path2 = Paths.get(tempDir, randFilename).toAbsolutePath().normalize();
        copyingFile = path2.toFile();
        InputStream is = null;
        OutputStream os = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource(path + fileName);
            is = classPathResource.getInputStream();
            os = new FileOutputStream(copyingFile);
            byte[] buffer = new byte[1024*4];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
        	if (is != null) {
        		try {
        			is.close();
				} catch (Exception e) {
				}
        	}
        	if (os != null) {
        		try {
        			os.close();
				} catch (Exception e) {
				}
        	}
        }
        
        XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(copyingFile);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
        return workbook;
    }
    
    public static void setDownloadExcelFile(HttpServletResponse response, String filename) throws IOException {
        OutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition",
                String.format("attachment; filename=%s", URLEncoder.encode(filename, "UTF-8")));
        if (out != null) {
            out.flush();
        }
    }
    
    
    public static void downloadExcel(SXSSFWorkbook wb, HttpServletResponse response, String filename) throws IOException {
        OutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition",
                String.format("attachment; filename=%s", URLEncoder.encode(filename, "UTF-8")));
        if (null != out) {
            wb.write(out);
            out.flush();
        }
    }
    
    public static void downloadExcel(XSSFWorkbook wb, HttpServletResponse response, String filename) throws IOException {
        OutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-disposition",
                String.format("attachment; filename=%s", URLEncoder.encode(filename, "UTF-8")));
        if (null != out) {
            wb.write(out);
            out.flush();
        }
    }
}
