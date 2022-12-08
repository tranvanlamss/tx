//package com.vietsoft.boot;
//
//import java.util.Optional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.vietsoft.common.enumerate.CustomerTypeEnum;
//import com.vietsoft.model.Customer;
//import com.vietsoft.model.CustomerMap;
//import com.vietsoft.model.User;
//import com.vietsoft.payload.CustomerForm;
//import com.vietsoft.repo.CustomerMapRepo;
//import com.vietsoft.repo.CustomerRepo;
//import com.vietsoft.repo.UserRepo;
//import com.vietsoft.response.MessageResp;
//import com.vietsoft.service.CustomerProfileService;
//
//@Configuration
//public class SetupCustomer {
//	@Value("${server.properties.boot.enable}")
//	boolean bootEnable;
//	static final Logger logger = LoggerFactory.getLogger(SetupCustomer.class);
//	String[] ps = { "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre",
//			"Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông",
//			"Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang",
//			"Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai",
//			"Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam",
//			"Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên",
//			"Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái",
//			"Phú Yên", "Cần Thơ", "Đà Nẵng", "Hải Phòng", "Hà Nội", "TP HCM" };
//
//	@Bean
//	CommandLineRunner loadCustomer(CustomerRepo customerRepo, UserRepo userRepo, CustomerMapRepo customerMapRepo, CustomerProfileService profileService) {
//		return args -> {
//			if (bootEnable) {
//				logger.info("loadCustomer.........");
//				long size = customerRepo.count();
//				if (size > 0) {
//					logger.info("loadCustomer exists {}", size);
//					return;
//				}
//
//				Optional<User> opDemo = userRepo.findByUsername("demo1");
//				if (!opDemo.isPresent()) {
//					logger.info("User NOT already exists");
//					return;
//				}
//
//				User user = opDemo.get();
//				logger.info("User already exists {}", user.getId());
//				int chi = 10;
//				int biz;
////			while ((chi = (int)Math.round(Math.random() * 100)) < 10) {
////				//continue
////			}
//				for (int i = 0; i < chi; i++) {
//					String bizRole;
//					biz = (int) Math.round(Math.random() * 100) % 3;
//					switch (biz) {
//						case 2:
//							bizRole = CustomerTypeEnum.SHOP.getValue();
//							break;
//						case 1:
//							bizRole = CustomerTypeEnum.AGENCY.getValue();
//							break;
//						case 0:
//						default:
//							bizRole = CustomerTypeEnum.DISTRIBUTER.getValue();
//							break;
//					}
//					biz = (int) Math.round(Math.random() * 10000) % ps.length;
//					String province = ps[biz];
//					String ix = "-" + (i + 1);
//					CustomerForm cust1 = this.createCustomerForm(ix, bizRole, province);
//					User user1 = profileService.createUser(cust1, user.getId());
//					MessageResp cust1Message = profileService.createCustomer(user1.getId(), cust1, null, user.getId());
//					Customer customer = (Customer) cust1Message.getResult();
//					if (bizRole == CustomerTypeEnum.DISTRIBUTER.getValue() || bizRole == CustomerTypeEnum.AGENCY.getValue()) {
//						int chj = (int) Math.round(Math.random() * 100) % 5;
//						for (int j = 0; j < chj; j++) {
//							if (bizRole == CustomerTypeEnum.DISTRIBUTER.getValue()) {
//								biz = (int) Math.round(Math.random() * 10000) % 2;
//								switch (biz) {
//									case 1:
//										bizRole = CustomerTypeEnum.SHOP.getValue();
//										break;
//									case 0:
//									default:
//										bizRole = CustomerTypeEnum.AGENCY.getValue();
//										break;
//								}
//							} else {
//								bizRole = CustomerTypeEnum.SHOP.getValue();
//							}
//
//							String ix2 = "-" + (i + 1) + "-" + (j + 1);
//							CustomerForm cust2 = this.createCustomerForm(ix2, bizRole, province);
//							User user2 = profileService.createUser(cust1, user.getId());
//							MessageResp custMessage2 = profileService.createCustomer(user2.getId(), cust2, null, user.getId());
//							Customer customer2 = (Customer) custMessage2.getResult();
//							CustomerMap customerMap = new CustomerMap();
//							customerMap.setChildrenId(customer2.getId());
//							customerMap.setParentId(customer.getId());
//							customerMapRepo.save(customerMap);
//							if (bizRole == CustomerTypeEnum.AGENCY.getValue()) {
//								int chl = (int) Math.round(Math.random() * 100) % 4;
//								for (int l = 0; l < chl; l++) {
//									bizRole = CustomerTypeEnum.SHOP.getValue();
//									biz = (int) Math.round(Math.random() * 10000) % ps.length;
//									province = ps[biz];
//									String ix3 = "-" + (i + 1) + "-" + (j + 1) + "-" + (l + 1);
//									CustomerForm cust3 = this.createCustomerForm(ix3, bizRole, province);
//									User user3 = profileService.createUser(cust3, user.getId());
//									profileService.createCustomer(user3.getId(), cust3, null, user.getId());
//									Customer customer3 = (Customer) custMessage2.getResult();
//									CustomerMap customerMap1 = new CustomerMap();
//									customerMap1.setChildrenId(customer3.getId());
//									customerMap1.setParentId(customer2.getId());
//									customerMapRepo.save(customerMap1);
//								}
//							}
//						}
//					}
//				}
//
//				logger.info("loadCustomer exists {}", chi);
//			}
//		};
//	}
//	private CustomerForm createCustomerForm(String index, String bizRole, String province){
//		CustomerForm customerForm = new CustomerForm();
//		customerForm.setCode("Child-"+bizRole + "-" + index);
//		customerForm.setName("Customer Name-"+index);
//		customerForm.setProvince(province);
//		customerForm.setBizRole(bizRole);
//		customerForm.setAddress("address-"+index);
//		customerForm.setPhone("0987654321");
//		customerForm.setDescription("Just a test!");
//		customerForm.setEmail("email"+ index +"@gmail.com");
//		return customerForm;
//	}
//}
