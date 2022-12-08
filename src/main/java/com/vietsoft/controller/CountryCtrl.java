package com.vietsoft.controller;

import java.util.Optional;

import javax.transaction.Transactional;

import com.vietsoft.repo.ProvinceRepo;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vietsoft.common.Constants;
import com.vietsoft.common.UserUtil;
import com.vietsoft.common.enumerate.ErrorCodesEnum;
import com.vietsoft.model.master.Country;
import com.vietsoft.repo.CountryRepo;
import com.vietsoft.repo.DistrictRepo;
import com.vietsoft.repo.WardRepo;
import com.vietsoft.response.MessageResp;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/v1/countries")
@Transactional
public class CountryCtrl {

	static final Logger logger = LoggerFactory.getLogger(CountryCtrl.class);

	@Autowired
	CountryRepo countryRepo;
	@Autowired
	ProvinceRepo provinceRepo;
	@Autowired
    DistrictRepo districtRepo;
    @Autowired
    WardRepo wardRepo;

	@RequestMapping(path = "", method = RequestMethod.GET)
	@ApiOperation(value = "Get All Countries", response = MessageResp.class)
	public HttpEntity<Object> getAll(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		if (page == null || page <= 0) {
			page = 0;
		}
		if (size == null || size <= 0) {
			size = Constants.ITEM_PER_PAGE;
		}
		Pageable pageable = PageRequest.of(page, size);
		Page<Country> items = countryRepo.findAll(pageable);
		return ResponseEntity.ok(new MessageResp(items));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Country", response = MessageResp.class)
	public HttpEntity<Object> getById(@PathVariable("id") String id) {
		if (StringUtils.isEmpty(id)) {
			logger.info("NOT valid input parameters");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp()
					.error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
		}
		Country item1 = null;
		Optional<Country> opItem = countryRepo.findById(id);
		if (opItem.isPresent()) {
			item1 = opItem.get();
		}
		return ResponseEntity.ok(new MessageResp(item1));
	}

	@RequestMapping(path = "/{provinceId}/districts", method = RequestMethod.GET)
    @ApiOperation(value = "Get districts", response = MessageResp.class)
    public HttpEntity<Object> getDistricts(@PathVariable String provinceId) {
        return ResponseEntity.ok(new MessageResp(districtRepo.findByProvinceId(provinceId)));
    }
    @RequestMapping(path = "/{districtId}/wards", method = RequestMethod.GET)
    @ApiOperation(value = "Get wards", response = MessageResp.class)
    public HttpEntity<Object> getWards(@PathVariable String districtId) {
        return ResponseEntity.ok(new MessageResp(wardRepo.findByDistrictId(districtId)));
    }
}
