package com.vietsoft.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vietsoft.model.master.Province;
import com.vietsoft.repo.ProvinceRepo;


@Configuration
public class SetupProvinces {
	static final Logger logger = LoggerFactory.getLogger(SetupProvinces.class);
	@Value("${server.properties.boot.enable}")
	boolean bootEnable;
	@Bean
	CommandLineRunner loadProvinces(ProvinceRepo repo) {
		return args -> {
			if (bootEnable) {
				logger.info("Load Provinces.........");
				long size = repo.count();
				if (size > 0) {
					logger.info("Province exists {}", size);
					return;
				}
				String[] ps = {"An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre",
						"Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Đắk Lắk", "Đắk Nông",
						"Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang",
						"Hòa Bình", "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai",
						"Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam",
						"Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên",
						"Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái",
						"Phú Yên", "Cần Thơ", "Đà Nẵng", "Hải Phòng", "Hà Nội", "TP HCM"};
				String area;
				for (String name1 : ps) {
					if (name1 == "Bắc Giang" || name1 == "Bắc Kạn" || name1 == "Bắc Ninh" || name1 == "Cao Bằng" || name1 == "Điện Biên" || name1 == "Hà Giang" ||
							name1 == "Hà Nam" || name1 == "Hà Nội" || name1 == "Hải Dương" || name1 == "Hải Phòng" || name1 == "Hòa Bình" || name1 == "Hưng Yên" ||
							name1 == "Lai Châu" || name1 == "Lạng Sơn" || name1 == "Lào Cai" || name1 == "Nam Định" || name1 == "Ninh Bình" || name1 == "Phú Thọ" ||
							name1 == "Quảng Ninh" || name1 == "Sơn La" || name1 == "Thái Bình" || name1 == "Thái Nguyên" || name1 == "Tuyên Quang" || name1 == "Vĩnh Phúc" ||
							name1 == "Yên Bái") {
						area = "Miền Bắc";
					} else if (name1 == "An Giang" || name1 == "Bà Rịa - Vũng Tàu" || name1 == "Bạc Liêu" || name1 == "Bến Tre" || name1 == "Bình Dương" ||
							name1 == "Bình Phước" || name1 == "Cà Mau" || name1 == "Cần Thơ" || name1 == "Đồng Nai" || name1 == "Đồng Tháp" || name1 == "Hậu Giang" ||
							name1 == "Kiên Giang" || name1 == "Long An" || name1 == "Sóc Trăng" || name1 == "Tây Ninh" || name1 == "Tiền Giang" || name1 == "TP HCM" ||
							name1 == "Trà Vinh" || name1 == "Vĩnh Long") {
						area = "Miền Nam";
					} else {
						area = "Miền Trung";
					}
					Province ar = new Province();
					ar.name(name1).area(area);
					repo.save(ar);
				}
			}
		};
	}
}
