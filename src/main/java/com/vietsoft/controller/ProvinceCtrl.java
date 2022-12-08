//package com.vietsoft.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import javax.transaction.Transactional;
//import javax.validation.Valid;
//
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.vietsoft.common.Constants;
//import com.vietsoft.common.Utils;
//import com.vietsoft.common.enumerate.ErrorCodesEnum;
//import com.vietsoft.model.District;
//import com.vietsoft.model.Province;
//import com.vietsoft.model.Ward;
//import com.vietsoft.payload.ProvinceForm;
//import com.vietsoft.repo.DistrictRepo;
//import com.vietsoft.repo.ProvinceRepo;
//import com.vietsoft.repo.WardRepo;
//import com.vietsoft.response.MessageResp;
//
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping(path = "/api/v1/provinces")
//@Transactional
//public class ProvinceCtrl {
//
//	static final Logger logger = LoggerFactory.getLogger(ProvinceCtrl.class);
//
//	@Autowired
//	ProvinceRepo provinceRepo;
//	@Autowired
//	DistrictRepo districtRepo;
//	@Autowired
//	WardRepo wardRepo;
//	@RequestMapping(path = "", method = RequestMethod.GET)
//	@ApiOperation(value = "Get Provinces", response = MessageResp.class)
//	public HttpEntity<Object> getAll(@RequestParam(required = false) Integer page,
//			@RequestParam(required = false) Integer size) {
//		if (page == null || page <= 0) {
//			page = 0;
//		}
//		if (size == null || size <= 0) {
//			size = Constants.MASTER_ITEM_PER_PAGE;
//		}
//		Pageable pageable = PageRequest.of(page, size);
//		Page<Province> provs = provinceRepo.findAll(pageable);
//		return ResponseEntity.ok(new MessageResp(provs));
//	}
//	@RequestMapping(path = "/areas", method = RequestMethod.GET)
//	@ApiOperation(value = "Get Areas", response = MessageResp.class)
//	public HttpEntity<Object> getAreas() {
//		return ResponseEntity.ok(new MessageResp(provinceRepo.getAllAreas()));
//	}
//	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
//	@ApiOperation(value = "Get Provinces By ID", response = MessageResp.class)
//	public HttpEntity<Object> getById(@PathVariable("id") String id) {
//		if (StringUtils.isEmpty(id)) {
//			logger.info("NOT valid input parameters");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
//		}
//		Province prov1 = null;
//		Optional<Province> opProv = provinceRepo.findById(id);
//		if (opProv.isPresent()) {
//			prov1 = opProv.get();
//		}
//		return ResponseEntity.ok(new MessageResp(prov1));
//	}
//
//	@RequestMapping(path = "", method = RequestMethod.POST)
//	@ApiOperation(value = "Add Province", response = MessageResp.class)
//	public HttpEntity<Object> add(@Valid @RequestBody ProvinceForm payload) {
//		if (StringUtils.isEmpty(payload.getName())) {
//			logger.info("NOT valid input parameters");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
//		}
//		Province prov1 = new Province();
//		prov1.setName(payload.getName());
//		prov1 = provinceRepo.save(prov1);
//		return ResponseEntity.ok(new MessageResp(prov1));
//	}
//
//	@RequestMapping(path = "", method = RequestMethod.PUT)
//	@ApiOperation(value = "Update Province", response = MessageResp.class)
//	public HttpEntity<Object> update(@Valid @RequestBody ProvinceForm payload) {
//		if (StringUtils.isEmpty(payload.getId())||StringUtils.isEmpty(payload.getName())) {
//			logger.info("NOT valid input parameters");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
//		}
//		Optional<Province> opProv = provinceRepo.findById(payload.getId());
//		if (!opProv.isPresent()) {
//			logger.info("NOT valid ID");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_ID).message("NOT valid ID"));
//		}
//		Province prov1 = opProv.get();
//		prov1.setName(payload.getName());
//		prov1 = provinceRepo.save(prov1);
//		return ResponseEntity.ok(new MessageResp(prov1));
//	}
//	
//	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
//	@ApiOperation(value = "Remove Province By ID", response = MessageResp.class)
//	public HttpEntity<Object> deleteById(@PathVariable("id") String id) {
//		if (StringUtils.isEmpty(id)) {
//			logger.info("NOT valid input parameters");
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
//		}
//		provinceRepo.deleteById(id);
//		logger.info("deleted!");
//		return ResponseEntity.ok(new MessageResp("success"));
//	}
//
//	@PreAuthorize("hasAnyRole('SUPER', 'ADMIN', 'PRODUCER-SALE_MANAGER')")
//	@RequestMapping(path = "/import/excel", method = RequestMethod.POST)
//	@ApiOperation(value = "Import from excel to JSON", response = MessageResp.class)
//	public HttpEntity<MessageResp> importDistrictWard(@RequestParam MultipartFile file, @RequestParam Integer sheet, @RequestParam Integer colProvince, @RequestParam Integer colDistrict, @RequestParam Integer colWard) {
//		return readFromExcel(file, sheet, colProvince, colDistrict, colWard);
//
//	}
//	private HttpEntity<MessageResp> readFromExcel(MultipartFile file, Integer sheet, Integer colProvince, Integer colDistrict, Integer colWard) {
//		Workbook workbook = Utils.getWorkbook(file);
//		Map<String, District> map = new HashMap<>();
//		Sheet worksheet = workbook.getSheetAt(sheet);
//		List<District> districts = new ArrayList<>();
//		List<Ward> wards = new ArrayList<>();
//
//		for(int indexRow = 0; indexRow < worksheet.getPhysicalNumberOfRows(); indexRow++) {
//			Row row = worksheet.getRow(indexRow);
//			if(row != null) {
//				District district = new District();
//				Ward ward = new Ward();
//				String name = Utils.getCellValue(workbook, row, colWard);
//				String districtName = Utils.getCellValue(workbook, row, colDistrict);
//				String provinceId = Utils.getCellValue(workbook, row, colProvince);
////				provinceName = provinceName.replace("Tỉnh ", "").replace("Thành phố ", "");
////				provinceName = provinceName.replace("Thành phố Hồ Chí Minh", "TP HCM");
//				if (!StringUtils.isEmpty(name)) {
//					ward.setName(name);
//					ward.setProvinceId(provinceId);
//					ward.setDistrict(districtName);
//					wards.add(ward);
//				}
//				if (!StringUtils.isEmpty(districtName)) {
//					if (map.get(districtName) == null) {
//						district.setName(districtName);
//						district.setProvinceId(provinceId);
//						map.put(districtName, district);
//					}
//				}
//			}
//		}
////		List<String> districtNames = map.values().stream().map(v->v.getName()).collect(Collectors.toList());
////		List<District> districtList = districtRepo.findAllByNameIn(districtNames);
////		if (districtList.isEmpty()) {
//			districtRepo.saveAll(map.values());
////		}
////		List<String> wardNames = wards.stream().map(v->v.getName()).collect(Collectors.toList());
////		List<Ward> wardList = wardRepo.findAllByNameIn(wardNames);
////		if (wardList.size() == 0) {
////			wardRepo.saveAll(wards);
////		}
//
//		Map<String, Object> content = new HashMap<>();
//		content.put("districts", map.values());
//		content.put("districts-size", map.values().size());
//		content.put("wards", wards);
//		content.put("wards-size", wards.size());
//
//		return ResponseEntity.ok(new MessageResp(content));
//	}
//}