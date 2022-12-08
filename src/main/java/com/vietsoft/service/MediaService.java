//package com.vietsoft.service;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.Date;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.vietsoft.common.enumerate.FileTypeEnum;
//import com.vietsoft.model.Media;
//import com.vietsoft.repo.MediaRepo;
//import com.vietsoft.response.IpfsResp;
//
//@Service
//public class MediaService {
//
//    static final Logger logger = LoggerFactory.getLogger(MediaService.class);
//
//    @Autowired
//    private FileService fileService;
//
//    @Autowired
//    private MediaRepo mediaRepo;
//
//    public Media createMedia(String orgId, String createrId, String folderName,
//                             MultipartFile file, HttpServletRequest request) {
//        Media media=null;
//        if (file != null && !file.isEmpty()) {
//            try {
//                media = createMedia(orgId, createrId, folderName, file.getOriginalFilename(), file.getBytes(), file.getContentType(), request);
////                media = createMedia(file, folderName);
//            } catch (Exception e) {
//                logger.error("Error when trying create media file" + e.getMessage());
//            }
//        }
//        return media;
//    }
//
//    public Media createMedia(String orgId, String createrId, String folderName, String fileName,
//                             byte[] fileBytes, String contentType, HttpServletRequest request) {
//        Media media=null;
//
//        try {
//            IpfsResp info = fileService.add(fileName, fileBytes, request);
//            if (info == null || (info.getHash() == null && info.getMessage() == null)) {
//                logger.error("Create media file fail.");
//            } else {
//                String hash = info.getHash();
//                String path = "/api/v1/files/" + folderName + "/" + hash;
//
//                logger.info("Return {}, {}, {}", info.getName(), hash, info.getSize());
//
//                media = new Media();
//                media.setName(fileName);
//                media.setHash(hash);
//                media.setSize(info.getSize());
//                media.setContactId(folderName);
//                media.setType(contentType);
//                media.setUrl(path);
//                media.setCreatedTime(new Date());
//                media.setUpdatedTime(new Date());
//                media.setCreaterId(createrId);
//                media.setUpdaterId(createrId);
//                media = mediaRepo.save(media);
//
//            }
//        } catch (URISyntaxException | IOException e) {
//            logger.error("Error when trying create media file" + e.getMessage());
//        }
//
//        return media;
//    }
//
//    public Media createMedia(String orgId, String createrId, MultipartFile file, String folderName) {
//
//        Media media=null;
//
//        if (file != null && !file.isEmpty()) {
//
//            IpfsResp info = fileService.add(file, folderName, FileTypeEnum.IMAGE);
//
//            if (info == null || (info.getHash() == null && info.getMessage() == null)) {
//                logger.error("Create media file fail.");
//            } else {
//                String hash = info.getHash();
//                String path = "/api/v1/files/" + folderName + "?hash=" + hash;
//
//                logger.info("Return {}, {}, {}", info.getName(), hash, info.getSize());
//
//                media = new Media();
//                media.setName(info.getName());
//                media.setHash(hash);
//                media.setSize(info.getSize());
//                media.setContactId(folderName);
//                media.setType(info.getType());
//                media.setUrl(path);
//                media.setCreatedTime(new Date());
//                media.setUpdatedTime(new Date());
//                media.setCreaterId(createrId);
//                media.setUpdaterId(createrId);
//                media = mediaRepo.save(media);
//
//            }
//        }
//
//        return media;
//    }
//}
