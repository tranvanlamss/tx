package com.vietsoft.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Component("XCoderBuilder")
public class XCoderBuilder {
	
	public enum XCodeOutputType {
		JPEG, PNG, BMP, GIF
	}
	
	private static int BUFF_SIZE = 1024*4;
	private static int MAX_SIZE = 1024*4;
	private static int MIN_SIZE = 64;
	
	private static Map<EncodeHintType, Object> hints = null;
	private static Map<EncodeHintType, Object> getCustomHints() {
		if (hints == null) {
			hints = new Hashtable<EncodeHintType, Object>();
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 0);
		}
		return hints;
	}
	
	private static int refineSize(int z) {
		if (z > MAX_SIZE) {
			z = MAX_SIZE;
		} else if (z < MIN_SIZE) {
			z = MIN_SIZE;
		}
		return z;
	}
	
	public static String newQRCodeFile(String text, int size, XCodeOutputType contentType)
            throws WriterException, IOException {
		size = refineSize(size);

		Writer writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size, getCustomHints());

        String tempDir = System.getProperty("java.io.tmpdir");
        String randFilename = UUID.randomUUID().toString();
        Path path = Paths.get(tempDir, randFilename).toAbsolutePath().normalize();
        MatrixToImageWriter.writeToPath(bitMatrix, contentType.toString(), path);
        return path.toString();
    }
	
	public static ByteArrayOutputStream newQRCode(String text, int size, XCodeOutputType contentType) throws WriterException, IOException {
		ByteArrayOutputStream outStream = null;

		size = refineSize(size);
		
		Writer writer = new MultiFormatWriter();
		BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size, getCustomHints());

		outStream = new ByteArrayOutputStream(BUFF_SIZE);
		MatrixToImageWriter.writeToStream(bitMatrix, contentType.toString(), outStream);
	    
	    return outStream;
	}

	public static ByteArrayOutputStream newQRCodePDF(String text, int size, XCodeOutputType contentType) throws WriterException, IOException, DocumentException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(BUFF_SIZE);
	    ByteArrayOutputStream imageStream = null;
	    Document document = null;

	    try {
	    	document = new Document();
		    PdfWriter.getInstance(document, outStream);
		    document.open();
		         
	    	imageStream = newQRCode(text, size, contentType);
		    Image image = Image.getInstance(imageStream.toByteArray());
		    document.add(image);
	    } finally {
	    	if (imageStream != null) {
				try {
					imageStream.close();
				} catch (IOException e) {
				}
	    	}
			try {
				document.close();
			} catch (Exception e) {
			}
	    }
	    
	    return outStream;
	}
	
	public static void newQRCodePipe(String text, int size, XCodeOutputType contentType, final OutputStream outStream) throws WriterException, IOException {
		size = refineSize(size);
		
		Writer writer = new MultiFormatWriter();
		BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size, getCustomHints());

		MatrixToImageWriter.writeToStream(bitMatrix, contentType.toString(), outStream);
	}

	public static void newQRCodePDFPipe(String text, int size, XCodeOutputType contentType, final OutputStream outStream) throws WriterException, IOException, DocumentException {
	    ByteArrayOutputStream imageStream = null;
	    Document document = null;

	    try {
	    	document = new Document();
		    PdfWriter.getInstance(document, outStream);
		    document.open();
		         
	    	imageStream = newQRCode(text, size, contentType);
		    Image image = Image.getInstance(imageStream.toByteArray());
		    document.add(image);
	    } finally {
	    	if (imageStream != null) {
				try {
					imageStream.close();
				} catch (IOException e) {
				}
	    	}
			try {
				document.close();
			} catch (Exception e) {
			}
	    }
	}
}
