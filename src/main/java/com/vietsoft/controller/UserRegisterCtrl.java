package com.vietsoft.controller;

import com.vietsoft.common.TokenUtil;
import com.vietsoft.common.enumerate.ErrorCodesEnum;
import com.vietsoft.common.enumerate.RoleEnum;
import com.vietsoft.common.enumerate.UserStatusEnum;
import com.vietsoft.model.User;
import com.vietsoft.model.UserRole;
import com.vietsoft.payload.user.ForgotPassword;
import com.vietsoft.payload.user.RegisterUser;
import com.vietsoft.payload.user.SetPassword;
import com.vietsoft.payload.user.VerifyEmail;
import com.vietsoft.repo.UserRepo;
import com.vietsoft.repo.UserRoleRepo;
import com.vietsoft.response.MessageResp;
import com.vietsoft.service.MailSenderService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/reg")
public class UserRegisterCtrl {

	static final Logger logger = LoggerFactory.getLogger(UserRegisterCtrl.class);
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Autowired
	private MailSenderService mailSender;

	@Autowired
	private XUtilsCtrl recaptchaService;

	@Autowired
	PasswordEncoder passwordEncoder;
    @RequestMapping(path = "/captcha", method = RequestMethod.GET)
    @ApiOperation(value = "Generate captcha", response = MessageResp.class)
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //recaptchaService.captcha(request, response);
    }
	
	@RequestMapping(path = "", method = RequestMethod.POST)
	@ApiOperation(value = "Register a new user", response = MessageResp.class)
	public HttpEntity<Object> register(@Valid @RequestBody RegisterUser payload) {
//		int captMatched = UserUtil.checkCapt(payload.getCaptkey(), payload.getCaptcha());
//		if (captMatched != 0) {
//			logger.info("Captcha does NOT matched");
//			if (captMatched == 1) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new MessageResp().error(ErrorCodesEnum.CAPTCHA_NOT_MATCH.getValue())
//                                .message("Captcha does NOT matched"));
//			} else {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new MessageResp().error(ErrorCodesEnum.CAPTCHA_EXPIRED.getValue())
//                                .message("Captcha is expired, it is valid within 2 minutes"));
//			}
//		}
		
		String email = payload.getEmail().trim().toLowerCase();
		String username = payload.getUsername().trim().toLowerCase();
		//String orgId = UserUtil.getOrgId();

		if (StringUtils.isEmpty(email)) {
			logger.info("Email can NOT be empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.EMAIL_EMPTY).message("Email can NOT be empty"));
		}
		if (StringUtils.isEmpty(username)) {
			logger.info("Username can NOT be empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.USERNAME_EMPTY).message("Username can NOT be empty"));
		}
		if (StringUtils.isEmpty(payload.getPassword())) {
			logger.info("Password can NOT be empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.PASSWORD_EMPTY).message("Password can NOT be empty"));
		}

		Optional<User> userByUsername = userRepo.findByUsername(username);
		if(userByUsername.isPresent()){
			logger.info("Username already register");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.USERNAME_ALREADY_REGISTED).message("Username already register"));
		}

		Optional<User> userByEmail = userRepo.findFirstByEmail(email);
		if(userByEmail.isPresent()){
			logger.info("Email already register");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.EMAIL_ALREADY_REGISTED).message("Email already register"));
		}

		String firstName = payload.getYourName();
		String lastName = "";
		String names[] = firstName.split("[\\;\\,\\s]");
		if (names.length > 1) {
			lastName = names[names.length - 1];
			firstName = firstName.substring(0, (firstName.lastIndexOf(lastName) - 1));
		}
		firstName = firstName.trim();
		lastName = lastName.trim();
		String emailVerifiedToken = TokenUtil.generateToken(64);

		User user = new User();
		user.setUsername(username);
		user.setPassword(payload.getPassword());
		user.setEmail(payload.getEmail());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setFullName(payload.getYourName());
		user.setShortName(payload.getShortName());
		user.setPhone(payload.getPhoneNumber());

		user.setEmailVerified(false);
		user.setEmailVerifiedToken(emailVerifiedToken);
		user.setStatus(UserStatusEnum.ACTIVATED.getValue());
		user.setUpdatedTime(LocalDateTime.now());
		user.setCreatedTime(LocalDateTime.now());

		user = userRepo.save(user);
		// add role
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		userRole.setRole(RoleEnum.USER.name());
		userRoleRepo.save(userRole);

		try {
			mailSender.sendVerifyingReq(user);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new MessageResp(user));
	}

	@RequestMapping(path = "/confirm", method = RequestMethod.POST)
	@ApiOperation(value = "Registered user, who do confirm their email address", response = MessageResp.class)
	public HttpEntity<Object> emailConfirm(@Valid @RequestBody VerifyEmail payload) {
		if (StringUtils.isEmpty(payload.getUid()) || StringUtils.isEmpty(payload.getToken())) {
			logger.info("NOT valid input parameters");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
		}
		Optional<User> userOpt = userRepo.findById(payload.getUid());
		if (!userOpt.isPresent()) {
			logger.info("NOT valid user ID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_USER_ID).message("NOT valid current User ID"));
		}
		User user = userOpt.get();
		if (!payload.getToken().equals(user.getEmailVerifiedToken())) {
			logger.info("NOT valid user Token");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_TOKEN).message("NOT valid user Token"));
		}
		user.setEmailVerifiedToken("");
		user.setEmailVerified(true);
		userRepo.save(user);
		// mailSender.sendWelcomeNewMember(user.getEmail(), "", "", "");
		return ResponseEntity.ok(new MessageResp(user));
	}

	@RequestMapping(path = "/resend/confirm", method = RequestMethod.POST)
	@ApiOperation(value = "Registered user, who want to retrieve the confirm email address again", response = MessageResp.class)
	public HttpEntity<Object> resendEmailConfirm(@Valid @RequestBody ForgotPassword payload) {
		String email = payload.getEmail().trim().toLowerCase();
		if (StringUtils.isEmpty(email)) {
			logger.info("Email can NOT be empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.EMAIL_EMPTY).message("Email can NOT be empty"));
		}
		Optional<User> opUser = userRepo.findFirstByEmail(email);
		if (!opUser.isPresent()) {
			logger.info("NOT valid user Email-ID, This email does NOT exist");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageResp().error(ErrorCodesEnum.FIND_NOT_FOUND).message("NOT valid user Email-ID, This email does NOT exist"));
		}
		User user = opUser.get();
		String authToken = TokenUtil.generateToken(64);
		user.setEmailVerifiedToken(authToken);
		user = userRepo.save(user);

		try {
			mailSender.sendVerifyingReq(user);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new MessageResp(user));
	}

	@RequestMapping(path = "/forgot-passwd", method = RequestMethod.POST)
	@ApiOperation(value = "Send an email for recovery password of user who forgot password", response = MessageResp.class)
	public HttpEntity<Object> forgotPass(@Valid @RequestBody ForgotPassword payload) {
		logger.info("forgotPass: {}", payload);
		String email = payload.getEmail().trim().toLowerCase();
		if (StringUtils.isEmpty(email)) {
			logger.info("Email can NOT be empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.EMAIL_EMPTY).message("Email can NOT be empty"));
		}
		Optional<User> opUser = userRepo.findFirstByEmail(email);
		if (!opUser.isPresent()) {
			logger.info("NOT valid user Email-ID, This email does NOT exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.EMAIL_DOES_NOT_EXISTED).message("NOT valid user Email-ID, This email does NOT exist"));
		}
		User user = opUser.get();
		if (!user.getEmailVerified()){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.EMAIL_NOT_VERIFIED).message("EMAIL_NOT_VERIFIED"));
		}
		String authToken = TokenUtil.generateToken(64);

		try {
			mailSender.sendResetPasswordReq(user, authToken);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new MessageResp(user));
	}

	@RequestMapping(path = "/forgot-password/set-new", method = RequestMethod.POST)
	@ApiOperation(value = "Set new password for user who forgot their password", response = MessageResp.class)
	public HttpEntity<Object> setNewPasswordByToken(@Valid @RequestBody SetPassword payload) {
		if (StringUtils.isEmpty(payload.getToken())) {
			logger.info("Token can NOT be empty");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_TOKEN).message("NOT valid user Token"));
		}
		if (StringUtils.isEmpty(payload.getYourPassword())){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.PASSWORD_EMPTY).message("Password can NOT be empty"));
		}
		if (!payload.getYourPassword().equals(payload.getRetypePassword())) {
			logger.info("Password can NOT be empty, Password and Retype password must be matched each other");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.PASSWORD_AND_RETYPE_PASSWORD_NOT_MATCH).message("Password and Retype password must be matched each other"));
		}
		String token = payload.getToken();
		Optional<User> opUser = userRepo.findByEmailVerifiedToken(token);
		if (!opUser.isPresent()) {
			logger.info("NOT valid Token");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_TOKEN).message("NOT valid user Token"));
		}
		User user = opUser.get();
		if (!user.getId().equals(payload.getUid())) {
			logger.info("NOT valid UID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_UID).message("NOT valid UID"));
		}
		user.setPassword(passwordEncoder.encode(payload.getYourPassword()));
		userRepo.save(user);
		return ResponseEntity.ok(new MessageResp(user));
	}
}
