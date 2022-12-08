package com.vietsoft.controller;

import com.vietsoft.common.Constants;
import com.vietsoft.common.enumerate.ErrorCodesEnum;
import com.vietsoft.model.CropJob;
import com.vietsoft.payload.CropJobCreateForm;
import com.vietsoft.repo.CropJobRepo;
import com.vietsoft.response.MessageResp;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/cropjob")
@Transactional
public class CropJobCtrl {

	static final Logger logger = LoggerFactory.getLogger(CropJobCtrl.class);

	@Autowired
	CropJobRepo cropJobRepo;

	@RequestMapping(path = "", method = RequestMethod.GET)
	@ApiOperation(value = "Get crop job", response = MessageResp.class)
	public HttpEntity<Object> getAll(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		if (page == null || page <= 0) {
			page = 0;
		}
		if (size == null || size <= 0) {
			size = Constants.ITEM_PER_PAGE;
		}
		Pageable pageable = PageRequest.of(page, size);
		Page<CropJob> cropJobPage = cropJobRepo.findAll(pageable);
		return ResponseEntity.ok(MessageResp.page(cropJobPage));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get crop job", response = MessageResp.class)
	public HttpEntity<Object> getById(@PathVariable("id") String id) {
		if (StringUtils.isEmpty(id)) {
			logger.info("NOT valid input parameters");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp()
					.error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
		}
		Optional<CropJob> cropJobOptional = cropJobRepo.findById(id);
		if (cropJobOptional.isPresent()) {
			return ResponseEntity.ok(new MessageResp(cropJobOptional.get()));
		} else {
			logger.info("cropJobOptional IS EMPTY");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp()
					.error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
		}
	}

	@RequestMapping(path = "", method = RequestMethod.POST)
	@ApiOperation(value = "Add crop job", response = MessageResp.class)
	public HttpEntity<Object> add(@Valid @RequestBody CropJobCreateForm payload) {
		CropJob entity = new CropJob();
		entity.setCode(payload.getCode());
		entity.setName(payload.getName());
		entity.setStartDate(payload.getStartDate());
		entity.setEndDate(payload.getEndDate());

		entity.setCreatedTime(LocalDateTime.now());
		entity.setCreatedBy("admin");
		cropJobRepo.save(entity);
		return ResponseEntity.ok(new MessageResp("success"));
	}

	@RequestMapping(path = "", method = RequestMethod.PUT)
	@ApiOperation(value = "Update crop job", response = MessageResp.class)
	public HttpEntity<Object> update(@Valid @RequestBody CropJobCreateForm payload) {
		String id = payload.getId();
		if (StringUtils.isEmpty(id)) {
			logger.info("NOT valid input parameters");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
		}
		Optional<CropJob> cropJobOptional = cropJobRepo.findById(id);
		if(!cropJobOptional.isPresent()){
			logger.info("NOT valid input parameters");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
		}
		CropJob entity = cropJobOptional.get();
		entity.setCode(payload.getCode());
		entity.setName(payload.getName());
		entity.setStartDate(payload.getStartDate());
		entity.setEndDate(payload.getEndDate());

		entity.setUpdatedTime(LocalDateTime.now());
		entity.setUpdatedBy("admin");
		cropJobRepo.save(entity);
		return ResponseEntity.ok(new MessageResp("success"));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Remove crop job By ID", response = MessageResp.class)
	public HttpEntity<Object> deleteById(@PathVariable("id") String id) {
		if (StringUtils.isEmpty(id)) {
			logger.info("NOT valid input parameters");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
		}
		logger.info("deleted!");
		return ResponseEntity.ok(new MessageResp("success"));
	}
}
