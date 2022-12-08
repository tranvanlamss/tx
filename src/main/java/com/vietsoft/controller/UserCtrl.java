package com.vietsoft.controller;

import com.vietsoft.common.Constants;
import com.vietsoft.common.UserUtil;
import com.vietsoft.common.enumerate.ErrorCodesEnum;
import com.vietsoft.model.User;
import com.vietsoft.payload.UserForm;
import com.vietsoft.payload.user.ChangePassword;
import com.vietsoft.repo.RoleRepo;
import com.vietsoft.repo.UserRepo;
import com.vietsoft.response.MessageResp;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/users")
@Transactional
public class UserCtrl {
    static final Logger logger = LoggerFactory.getLogger(UserCtrl.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(path = "", method = RequestMethod.GET)
    @ApiOperation(value = "Get Users", response = MessageResp.class)
    public HttpEntity<Object> getAll(@RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer size) {
        if (page == null || page <= 0) {
            page = 0;
        }
        if (size == null || size <= 0) {
            size = Constants.ITEM_PER_PAGE;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepo.findAll(pageable);
        return ResponseEntity.ok(new MessageResp(users));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get Users By ID", response = MessageResp.class)
    public HttpEntity<Object> getById(@PathVariable("id") String id) {
        if (StringUtils.isEmpty(id)) {
            logger.info("NOT valid input parameters");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
        }
        User user1 = null;
        Optional<User> opUser = userRepo.findById(id);
        if (opUser.isPresent()) {
            user1 = opUser.get();
        }
        return ResponseEntity.ok(new MessageResp(user1));
    }

    @PreAuthorize("hasAnyRole('SUPER', 'ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update User", response = MessageResp.class)
    public HttpEntity<Object> update(@Valid @RequestBody UserForm payload, @Valid @PathVariable String id) {
        if (StringUtils.isEmpty(id)) {
            logger.info("NOT valid input parameters");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
        }
        if (StringUtils.isEmpty(payload.getRole()) ||StringUtils.isEmpty(payload.getBizRole()) ) {
            logger.info("Biz Role and Role is required fiels");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.BIZ_ROLE_EMPTY).message("Biz Role and Role is required fiels"));
        }

        Optional<User> optionalUser = userRepo.findById(id);
        if(!optionalUser.isPresent()){
            logger.info("NOT valid ID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_ID).message("NOT valid ID"));
        }
        Optional<User> opUser;
        if (!StringUtils.isEmpty(payload.getEmail())) {
            opUser = userRepo.findFirstByEmail(payload.getEmail());
            if (opUser.isPresent() && !payload.getEmail().equalsIgnoreCase(optionalUser.get().getEmail())) {
                logger.info("NOT valid user Email-ID, This email is already registered");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.EMAIL_ALREADY_REGISTED).message("NOT valid user Email-ID, This email is already registered"));
            }
        }
        opUser = userRepo.findById(id);
        if (!opUser.isPresent()) {
            logger.info("NOT valid ID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_ID).message("NOT valid ID"));
        }
        String firstName = payload.getName();
        String lastName = "";
        String names[] = firstName.split("[\\;\\,\\s]");
        if (names.length > 1) {
            lastName = names[names.length - 1];
            firstName = firstName.substring(0, (firstName.lastIndexOf(lastName) - 1));
        }
        firstName = firstName.trim();
        lastName = lastName.trim();
        User user = opUser.get();
        // TODO set new properties
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setFullName(payload.getName());
        user.setShortName(payload.getShortName());
        user.setEmail(payload.getEmail());
        user.setUpdaterId(UserUtil.getUserId());
        user.setUpdatedTime(LocalDateTime.now());
        user.setPhone(payload.getPhone());

        user = userRepo.save(user);
        return ResponseEntity.ok(new MessageResp(user));
    }

    @PreAuthorize("hasAnyRole('SUPER', 'ADMIN')")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Remove User By ID", response = MessageResp.class)
    public HttpEntity<Object> deleteById(@PathVariable("id") String id) {
        if (StringUtils.isEmpty(id)) {
            logger.info("NOT valid input parameters");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
        }
        userRepo.deleteById(id);
        logger.info("deleted!");
        return ResponseEntity.ok(new MessageResp("success"));
    }

    @PreAuthorize("#payload.userId == authentication.principal.id")
    @RequestMapping(path = "/change-password", method = RequestMethod.POST)
    @ApiOperation(value = "User do change their password", response = MessageResp.class)
    public HttpEntity<Object> changePassword(@Valid @RequestBody ChangePassword payload) {
        if (StringUtils.isEmpty(payload.getUserId()) || StringUtils.isEmpty(payload.getPassword())
                || StringUtils.isEmpty(payload.getNewPassword())) {
            logger.info("NOT valid input parameters");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_INPUT_PARAMETER).message("NOT valid input parameters"));
        }
        if (payload.getPassword().equals(payload.getNewPassword())) {
            logger.info("New password is the same current one");
            return ResponseEntity.ok(null);
        }
        Optional<User> userOpt = userRepo.findById(payload.getUserId());
        if (!userOpt.isPresent()) {
            logger.info("NOT valid user ID");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_USER_ID).message("NOT valid current User ID"));
        }
        User user = userOpt.get();
        boolean isValidPassword = passwordEncoder.matches(payload.getPassword(), user.getPassword());
        if (!isValidPassword) {
            logger.info("NOT valid password, must be stopped now");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResp().error(ErrorCodesEnum.INVALID_CURRENT_PASSWORD).message("NOT valid current password, please try again"));
        }
        user.setPassword(payload.getNewPassword());
        userRepo.save(user);
        return ResponseEntity.ok(new MessageResp("success"));
    }
}
