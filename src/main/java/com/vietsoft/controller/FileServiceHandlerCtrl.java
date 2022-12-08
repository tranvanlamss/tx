package com.vietsoft.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.vietsoft.common.JsonUtil;
import com.vietsoft.common.UserUtil;
import com.vietsoft.common.enumerate.ErrorCodesEnum;
import com.vietsoft.common.enumerate.FileTypeEnum;
import com.vietsoft.config.IpfsProperties;
import com.vietsoft.model.Media;
import com.vietsoft.model.User;
import com.vietsoft.repo.MediaRepo;
import com.vietsoft.response.IpfsResp;
import com.vietsoft.response.MessageResp;

@RestController
@RequestMapping(path = "/api/v1/files")
public class FileServiceHandlerCtrl {

	static final Logger logger = LoggerFactory.getLogger(FileServiceHandlerCtrl.class);

	@Autowired
	ServletContext context;

	@Autowired
	MediaRepo mediaRepo;

	@Autowired
	IpfsProperties props;

	@Value("${server.resources.storagePath}")
	private String storagePath;
//
//	@RequestMapping(value = { "/{contactId}/{storageId}" }, method = RequestMethod.POST)
//	public ResponseEntity<?> upload(@PathVariable String contactId, @PathVariable String storageId,
//			@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request)
//			throws URISyntaxException, IOException {
//		User user = UserUtil.getCurrentUser();
//		if (user == null) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResp().error("UNAUTHORIZED")
//					.message("Authorization is required to do this action"));
//		}
//		String filename = file.getOriginalFilename();
//		String requestUri = "/add";
////		logger.info("request {} {}", method, requestUri);
//		URI uri = new URI(props.getScheme(), null, props.getHost(), props.getPort(), props.getPath(), null, null);
//		uri = UriComponentsBuilder.fromUri(uri).path(requestUri).query(request.getQueryString()).build(true).toUri();
////		logger.info("API request {} {}", method, uri.toString());
//		ContentDisposition dispos = ContentDisposition.builder("form-data").name("file").filename(filename).build();
//		MultiValueMap<String, String> fileDescription = new LinkedMultiValueMap<>();
//		fileDescription.add(HttpHeaders.CONTENT_DISPOSITION, dispos.toString());
//		HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileDescription);
//		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//		body.add("file", fileEntity);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//		HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
//		RestTemplate restTemplate = new RestTemplate();
//		try {
//			ResponseEntity<byte[]> resp = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, byte[].class);
////			logger.info("Exchange result {}", new String(resp.getBody()));
//			try {
//				IpfsResp info = JsonUtil.from(resp.getBody(), IpfsResp.class);
//				if (info == null || (info.getHash() == null && info.getMessage() == null)) {
//					IpfsResp res = new IpfsResp();
//					res.setMessage("Server(IPFS) internal exception");
//					res.setCode(0);
//					res.setType("Error");
//					return ResponseEntity.status(500).body(res);
//				} else {
//					String hash = info.getHash();
//					String path = "/api/v1/files/" + contactId + "/" + hash;
////					logger.info("Return {}, {}, {}", info.getName(), hash, info.getSize());
//					Media media = null;
//					Optional<Media> opMedia = mediaRepo.findFirstByHashAndContactId(hash, contactId);
//					if (opMedia.isPresent()) {
//						media = opMedia.get();
//						media.setType(file.getContentType());
//						media.setName(filename);
//						media.setUpdatedTime(LocalDateTime.now());
//						media.setUrl(path);
//					} else {
//						Media m = new Media();
//						m.setName(filename);
//						m.setHash(hash);
//						m.setSize(info.getSize());
//						m.setType(file.getContentType());
//						m.setUrl(path);
//						m.setCreatedTime(LocalDateTime.now());
//						m.setUpdatedTime(LocalDateTime.now());
//						m.setCreaterId(UserUtil.getUserId());
//						m.setUpdaterId(UserUtil.getUserId());
//						media = mediaRepo.save(m);
//					}
//					media = mediaRepo.save(media);
//					return ResponseEntity.ok(media);
//				}
//			} catch (IOException e) {
//				// return
//				// ResponseEntity.status(500).body(IpfsResp.builder().message("Server(JSONParser)
//				// internal exception").code(0).type("Error").build());
//			}
//			return resp;
//		} catch (HttpStatusCodeException e) {
//			logger.error("Error: {}, {}, {}", e.getRawStatusCode(), e.getMessage());
//			try {
//				IpfsResp info = JsonUtil.from(e.getResponseBodyAsByteArray(), IpfsResp.class);
//				if (info == null || (info.getHash() == null && info.getMessage() == null)) {
//					IpfsResp res = new IpfsResp();
//					res.setMessage("Server(IPFS) internal exception");
//					res.setCode(0);
//					res.setType("Error");
//					return ResponseEntity.status(500).body(res);
//				} else {
////					logger.info("Return {}, {}, {}", info.getMessage(), info.getCode(), info.getType());
//					return ResponseEntity.status(500).body(info);
//				}
//			} catch (IOException e1) {
//				// return
//				// ResponseEntity.status(500).body(IpfsResp.builder().message("Server(JSONParser)
//				// internal exception").code(0).type("Error").build());
//			}
//			return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
//					.body(e.getResponseBodyAsByteArray());
//		}
//	}
//	@RequestMapping(value = "/{folderName}", method = RequestMethod.GET)
//	@ResponseBody
//	public ResponseEntity<?> download(@PathVariable String folderName, @RequestParam String hash) throws IOException {
//		Optional<Media> opMedia = mediaRepo.findFirstByHashAndContactId(hash, folderName);
//		if (!opMedia.isPresent()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
//		}
//		FileSystemResource fileSystemResource = new FileSystemResource(FileTypeEnum.getResourcesPath(storagePath, FileTypeEnum.IMAGE) + folderName + "/" + opMedia.get().getName());
//		File file = fileSystemResource.getFile();
//		if (!file.isFile()) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.FIND_NOT_FOUND).message("FILE_NOT_FOUND"));
//		}
//		Path path = Paths.get(file.getAbsolutePath());
//		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
//		HttpHeaders headers = new HttpHeaders();
//		String type = "application/octet-stream";
//		String filename = "";
//		Media media = opMedia.get();
//		if (media.getName() != null) {
//			filename = media.getName();
//		}
//		if (media.getType() != null) {
//			type = media.getType();
//		}
//		if (type.startsWith("image/") || type.startsWith("text/")) {
//			headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
//		} else {
//			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
//		}
//		headers.set(HttpHeaders.CONTENT_TYPE, type);
//		return ResponseEntity.ok()
//				.headers(headers)
//				.body(resource);
//	}
//
//	@RequestMapping(value = "/{contactId}/{hash}", method = RequestMethod.GET)
//	@ResponseBody
//	public ResponseEntity<?> download(@PathVariable String contactId, @PathVariable String hash, HttpServletRequest request)
//			throws URISyntaxException {
//		String requestUri = "/cat";
//		URI uri = new URI(props.getScheme(), null, props.getHost(), props.getPort(), props.getPath(), null, null);
//		uri = UriComponentsBuilder.fromUri(uri).path(requestUri).query("arg=" + hash)// request.getQueryString()
//				.build(true).toUri();
//		HttpHeaders headers = new HttpHeaders();
//		Enumeration<String> headerNames = request.getHeaderNames();
//		while (headerNames.hasMoreElements()) {
//			String headerName = headerNames.nextElement();
//			headers.set(headerName, request.getHeader(headerName));
//		}
//		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
//		RestTemplate restTemplate = new RestTemplate();
//		try {
//			ResponseEntity<byte[]> resp = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, byte[].class);
//			Optional<Media> opMedia = mediaRepo.findFirstByHashAndContactId(hash, contactId);
//			HttpHeaders respHeaders = new HttpHeaders();
//			String type = "application/octet-stream";
//			String filename = "";
//			if (opMedia.isPresent()) {
//				Media media = opMedia.get();
//				if (media.getName() != null) {
//					filename = media.getName();
//				}
//				if (media.getType() != null) {
//					type = media.getType();
//				}
//			}
//			if (type.startsWith("image/") || type.startsWith("text/")) {
//				respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
//			} else {
//				respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
//			}
//			respHeaders.set(HttpHeaders.CONTENT_TYPE, type);
//			return ResponseEntity.ok().headers(respHeaders).body(resp.getBody());
//		} catch (HttpStatusCodeException e) {
//			try {
//				IpfsResp info = JsonUtil.from(e.getResponseBodyAsByteArray(), IpfsResp.class);
//				if (info == null || (info.getHash() == null && info.getMessage() == null)) {
//					IpfsResp res = new IpfsResp();
//					res.setMessage("Server(IPFS) internal exception");
//					res.setCode(0);
//					res.setType("Error");
//					return ResponseEntity.status(500).body(res);
//				} else {
//					return ResponseEntity.status(500).body(info);
//				}
//			} catch (IOException e1) {
//				// return
//				// ResponseEntity.status(500).body(IpfsResp.builder().message("Server(JSONParser)
//				// internal exception").code(0).type("Error").build());
//			}
//			return ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
//					.body(e.getResponseBodyAsByteArray());
//		}
//
//	}
//
//	@RequestMapping(value = { "/savePdfFile" }, consumes = { "multipart/form-data" }, method = RequestMethod.POST)
//	public ResponseEntity<?> saveOfferLetter(@RequestParam(value = "file", required = true) MultipartFile file,
//			HttpMethod method, HttpServletRequest request, HttpServletResponse response)
//			throws URISyntaxException, IOException {
//		String filename = file.getOriginalFilename();
//		String requestUri = "/add";
//		URI uri = new URI(props.getScheme(), null, props.getHost(), props.getPort(), props.getPath(), null, null);
//		uri = UriComponentsBuilder.fromUri(uri).path(requestUri).query(request.getQueryString()).build(true).toUri();
//		ContentDisposition dispos = ContentDisposition.builder("form-data").name("file").filename(filename).build();
//		MultiValueMap<String, String> fileDescription = new LinkedMultiValueMap<>();
//		fileDescription.add(HttpHeaders.CONTENT_DISPOSITION, dispos.toString());
//		HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileDescription);
//		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//		body.add("file", fileEntity);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//		HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
//		RestTemplate restTemplate = new RestTemplate();
//		String hash;
//		String path;
//		try {
//			ResponseEntity<byte[]> resp = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, byte[].class);
////			logger.info("Exchange result {}", new String(resp.getBody()));
//			IpfsResp info = JsonUtil.from(resp.getBody(), IpfsResp.class);
//			if (info == null || (info.getHash() == null && info.getMessage() == null)) {
//				IpfsResp res = new IpfsResp();
//				res.setMessage("Server(IPFS) internal exception");
//				res.setCode(0);
//				res.setType("Error");
//				return ResponseEntity.status(500).body(res);
//			} else {
//				hash = info.getHash();
//				path = "/api/v1/files/offerLetter/" + hash;
//				return  ResponseEntity.status(200).body(path);
//			}
//		} catch (Exception e) {
////			logger.error(e.getCause().getMessage());
//			return  ResponseEntity.status(500).body(e.getCause().getMessage());
//		}
//	}
}
