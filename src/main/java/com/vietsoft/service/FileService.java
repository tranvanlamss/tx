package com.vietsoft.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.vietsoft.common.FileUtil;
import com.vietsoft.common.JsonUtil;
import com.vietsoft.common.enumerate.FileTypeEnum;
import com.vietsoft.config.IpfsProperties;
import com.vietsoft.response.IpfsResp;

@Service
public class FileService {
    static final Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    IpfsProperties props;
    @Value("${server.resources.storagePath}")
    private String storagePath;

    public IpfsResp add(MultipartFile file, HttpServletRequest request)
            throws URISyntaxException, IOException {
        return add(file.getOriginalFilename(), file.getBytes(), request);
    }

    public IpfsResp add(String fileName, byte[] fileBytes, HttpServletRequest request)
            throws URISyntaxException, IOException {
        String requestUri = "/add";
        IpfsResp info;
        try {
            URI uri = new URI(props.getScheme(), null, props.getHost(), props.getPort(), props.getPath(), null, null);

            uri = UriComponentsBuilder.fromUri(uri).path(requestUri).query(request.getQueryString()).build(true).toUri();
            logger.info("API request {}", uri.toString());

            ContentDisposition dispos = ContentDisposition.builder("form-data").name("file").filename(fileName).build();

            MultiValueMap<String, String> fileDescription = new LinkedMultiValueMap<>();
            fileDescription.add(HttpHeaders.CONTENT_DISPOSITION, dispos.toString());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(fileBytes, fileDescription);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<byte[]> resp = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, byte[].class);
            logger.info("Exchange result {}", new String(resp.getBody()));
            info = JsonUtil.from(resp.getBody(), IpfsResp.class);
        } catch (HttpStatusCodeException e){
            info = JsonUtil.from(e.getResponseBodyAsByteArray(), IpfsResp.class);
        }
        return info;
    }

    public IpfsResp add(MultipartFile multipartFile, String folderName, FileTypeEnum fileType) {

        IpfsResp info = null;

        String path = FileTypeEnum.getResourcesPath(storagePath, fileType) + folderName + "/";
        String fileName = multipartFile.getOriginalFilename();
        logger.info("path images={}", path);
        File file = new File(path + fileName);

        if (!file.isFile()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Error when trying create new File " + e.getMessage());
            }
        }
        if (file.canWrite()) {
            String hash = "";
            String arrName[] = file.getName().split("\\.");
            String fileExtension = arrName[arrName.length - 1];
            try {
                FileUtils.writeByteArrayToFile(file, multipartFile.getBytes());
                hash = FileUtil.hash(file);
                file.renameTo(new File(path + hash + "." + fileExtension));
                file = new File(path + hash + "." + fileExtension);
            } catch (Exception e) {
                logger.error("Error when trying write from MultipartFile to File " + e.getMessage());
            }

            if (!StringUtils.isEmpty(hash)) {
                info = new IpfsResp();
                info.setName(file.getName());
                info.setSize(file.length());
                info.setType(multipartFile.getContentType());
                info.setHash(hash);
                info.setMessage("Success");
            }
        }

        return info;
    }
}
