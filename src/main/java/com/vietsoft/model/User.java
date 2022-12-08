package com.vietsoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user", indexes = {@Index(columnList = "email") })
public class User implements Serializable {
	private static final long serialVersionUID = -2371680769731694062L;

	@Id
	@GeneratedValue(generator = "objectid-generator")
	@GenericGenerator(name = "objectid-generator", strategy = "com.vietsoft.common.ObjectIDGenerator")
	@Column(unique = true, nullable = false, length = 24)
	private String id;

	@Column(nullable = false, length = 128, unique = true)
	private String username;

	@Column(length = 128)
	private String email;

	@Column(name = "first_name", length = 256)
	private String firstName;

	@Column(name = "last_name", length = 256)
	private String lastName;

	@Column(name = "full_name", length = 256)
	private String fullName;

	@Column(name = "short_name", length = 256)
	private String shortName;

	@Column(name = "avatar_url", length = 256)
	private String avatarUrl;

	LocalDate birthday;

	@Column(nullable = false, length = 256)
	@JsonIgnore
	private String password;

	@Column(length = 64)
	private String phone;

	@Column(nullable = false)
	private int status;// = UserStatusEnum.ACTIVATED.getValue()

	@Column(name = "email_verified", nullable = false)
	@JsonIgnore
	private Boolean emailVerified;

	@Column(name = "email_verified_token", length = 256)
	@JsonIgnore
	private String emailVerifiedToken;

	@Column(name = "updater_id", length = 24)
	private String updaterId;

	@Column(name = "creater_id", length = 24)
	private String createrId;

	@Column(name = "updated_time", nullable = false)
	private LocalDateTime updatedTime;

	@Column(name = "created_time", nullable = false)
	private LocalDateTime createdTime;
}