package com.vietsoft.controller;

import com.vietsoft.common.enumerate.ErrorCodesEnum;
import com.vietsoft.model.User;
import com.vietsoft.model.UserRole;
import com.vietsoft.payload.user.Credentials;
import com.vietsoft.repo.UserRepo;
import com.vietsoft.repo.UserRoleRepo;
import com.vietsoft.response.AuthenResp;
import com.vietsoft.response.MessageResp;
import com.vietsoft.response.UserAccessTokenResp;
import com.vietsoft.security.LocalTokenProvider;
import com.vietsoft.service.MessageSourceService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthenCtrl {

	static final Logger logger = LoggerFactory.getLogger(AuthenCtrl.class);
	@Autowired
	private LocalTokenProvider tokenProvider;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserRoleRepo userRoleRepo;
	
	@Autowired
	MessageSourceService messageSource;

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(path = "/local", method = RequestMethod.POST)
	@ApiOperation(value = "Authenticate with local credentials (email & password)", response = UserAccessTokenResp.class)
	public HttpEntity<Object> local(@RequestBody Credentials credentials, HttpServletRequest request,
			HttpServletResponse response) {
		String email = credentials.getEmail();
		Optional<User> opUser = userRepo.findByUsernameOrEmail(email, email);
		if (!opUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new AuthenResp().status(401).error(ErrorCodesEnum.UNAUTHORIZED.getValue()).message(messageSource.getMessage("error.messages.UNAUTHORIZED")));
		}
		User user = opUser.get();
		if (!user.getEmailVerified()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					new AuthenResp().status(401).error(ErrorCodesEnum.EMAIL_NOT_VERIFIED.getValue()).message(messageSource.getMessage("error.messages.EMAIL_NOT_VERIFIED")));
		}
		String password = credentials.getPassword();
		if(!passwordEncoder.matches(password, user.getPassword())){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					new AuthenResp().status(401).error(ErrorCodesEnum.INVALID_CREDENTIALS.name()).message(ErrorCodesEnum.INVALID_CREDENTIALS.getValue()));
		}
		List<UserRole> userRoles = userRoleRepo.findByUserId(user.getId());
		String token = tokenProvider.generateToken(user, userRoles);

		UserAccessTokenResp respUser = new UserAccessTokenResp();
		respUser.setUser(user);
		respUser.setToken(token);
		return ResponseEntity.ok(new MessageResp(respUser));
	}

}
