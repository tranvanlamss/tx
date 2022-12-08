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
//import com.vietsoft.common.enumerate.RoleEnum;
//import com.vietsoft.model.Role;
//import com.vietsoft.repo.RoleRepo;
//
//@Configuration
//public class SetupRoles {
//	static final Logger logger = LoggerFactory.getLogger(SetupRoles.class);
//	@Value("${server.properties.boot.enable}")
//	boolean bootEnable;
//	
//	boolean isCustomStarted = false;
//	@Bean
//	CommandLineRunner loadRoles(RoleRepo roleRepo) {
//		return args -> {
//			if (bootEnable || isCustomStarted) {
//				logger.info("Load Member Roles.........");
//
//				Role admin;
//				Optional<Role> opAdmin = roleRepo.findByValue(RoleEnum.ADMIN.getValue());
//				if (opAdmin.isPresent()) {
//					admin = opAdmin.get();
//					logger.info("Role already exists, {}, {}, {}", admin.getId(), admin.getName());
//					return;
//				}
//
//				for (RoleEnum ce : RoleEnum.values()) {
//					Role c = new Role();
//					c.setName(RoleEnum.getDescription(ce));
//					c.setValue(ce.getValue());
//					roleRepo.save(c);
//				}
//			}
//		};
//	}
//}
