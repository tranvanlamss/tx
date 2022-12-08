package com.vietsoft.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;



/**
 * The persistent class for the media database table.
 * 
 */
@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id", scope=String.class)
@Table(name="media")
public class Media implements Serializable {
	static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "objectid-generator") @GenericGenerator(name = "objectid-generator", strategy = "com.vietsoft.common.ObjectIDGenerator")
	@Column(unique=true, nullable=false, length=24)
	String id;

	@Column(length=256)
	String caption;

	@Column(nullable=false, length=256)
	String hash;

	@Column(nullable=false, length=256)
	String name;

	@Column(nullable=false)
	double size;

	@Column(nullable=false, length=256)
	String type;

	@Column(nullable=false, length=256)
	String url;

	@Column(name = "updater_id", length = 24)
	private String updaterId;

	@Column(name = "creater_id", length = 24)
	private String createrId;

	@Column(name = "updated_time", nullable = false)
	private LocalDateTime updatedTime;

	@Column(name = "created_time", nullable = false)
	private LocalDateTime createdTime;
}
