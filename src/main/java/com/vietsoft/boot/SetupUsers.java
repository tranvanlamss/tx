//package com.vietsoft.boot;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
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
//import com.vietsoft.common.enumerate.UserStatusEnum;
//import com.vietsoft.model.Organization;
//import com.vietsoft.model.Role;
//import com.vietsoft.model.User;
//import com.vietsoft.repo.OrganizationRepo;
//import com.vietsoft.repo.RoleRepo;
//import com.vietsoft.repo.UserRepo;
//
//@Configuration
//public class SetupUsers {
//	static final Logger logger = LoggerFactory.getLogger(SetupUsers.class);
//	@Value("${server.properties.boot.enable}")
//	boolean bootEnable;
//	
//	boolean isCustomStarted = false;
//	
//	protected final User createUser(final String username, final String orgId, final String firstName,
//			final String lastName, String email, UserRepo userRepo) {
//
//		Optional<User> opDemo = userRepo.findByUsername(username);
//		if (opDemo.isPresent()) {
//			logger.info("User already exists: {}", username);
//			return opDemo.get();
//		}
//		User user = new User();
//
//		user.setOrgId(orgId);
//		user.setUsername(username);
//		user.setPassword("D3m@4y0u1");
//		if (email == null || email.trim().length() == 0) {
//			user.setEmail(username + "@com.vn");
//		} else {
//			user.setEmail(email.trim().toLowerCase());
//		}
//
//		user.setFirstName(firstName);
//		user.setLastName(lastName);
//		user.setShortName(firstName);
//		user.setFullName(lastName + " " + firstName);
//		
//		user.setStatus(UserStatusEnum.ACTIVATED.getValue());
//		
//		user.setAvatarUrl("");
//		user.setBirthday(new Date());
//		user.setPhone("");
//		user.setPosition("");
//		
//		user.setEmailVerified(true);
//		user.setEmailVerifiedToken("");
//		user.setRegisterClass("");
//		user.setUserClass("");
//		user.setUpdatedTime(new Date());
//		user.setCreatedTime(new Date());
//		
//		user.setUpdaterId("");
//		user.setCreaterId("");
//		user.setLastLogin(new Date());
//		
//		user = userRepo.save(user);
//		return user;
//	}
//
//	protected final void createUserRole(final User user, RoleEnum role, UserRepo userRepo, RoleRepo roleRepo) {
//		Role curr = null;
//		while (curr == null) {
//			logger.info("Looking for User/Admin.........");
//			if (curr == null) {
//				Optional<Role> r = roleRepo.findByValue(role.getValue());
//				if (r.isPresent()) {
//					curr = r.get();
//				}
//			}
//
//			if (curr != null && user != null) {
//				logger.info("Save an admin User.........");
//				List<Role> roles = new ArrayList<Role>();
//				roles.add(curr);
//				user.setRoles(roles);
//				userRepo.save(user);
//			}
//
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//			}
//		}
//	}
//
//	@Bean
//	CommandLineRunner loadUsers(UserRepo userRepo, RoleRepo roleRepo, OrganizationRepo organizationRepo) {
//
//		return args -> {
//			if (bootEnable || isCustomStarted) {
//				logger.info("Load User.........");

//
//				String orgId = CodeGenService.BASE_ORG_ID;
//				User sysAdmin01 = createUser("viet.nghiem", orgId, "Admin", "System Admin", "nghiem.van.viet@vsi-international.com", userRepo);
//				User appAdmin01 = createUser("admin", orgId, "Admin", "Application Admin", "", userRepo);
//				
//				User demo1 = createUser("demo1", orgId, "John", "Smith", "", userRepo);
//				User demo2 = createUser("demo2", orgId, "Joe", "Biden", "", userRepo);
//				User demo3 = createUser("demo3", orgId, "Catherine", "Zeta-jones", "", userRepo);
//
//				createUserRole(sysAdmin01, RoleEnum.ADMIN, userRepo, roleRepo);
//				createUserRole(appAdmin01, RoleEnum.SUPER, userRepo, roleRepo);
//				
//				createUserRole(demo1, RoleEnum.SUPER, userRepo, roleRepo);
//				createUserRole(demo2, RoleEnum.STAFF, userRepo, roleRepo);
//				
//				createUserRole(demo3, RoleEnum.STORE_MANAGER, userRepo, roleRepo);
//
//			}
//		};
//	}
//}
