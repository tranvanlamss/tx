package com.vietsoft.model.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Immutable
@Table(name = "district")
@Data
public class District implements Serializable {
    private static final long serialVersionUID = 6737563576193471403L;

    @javax.persistence.Id
    @GeneratedValue(generator = "objectid-generator")
    @GenericGenerator(name = "objectid-generator", strategy = "com.vietsoft.common.ObjectIDGenerator")
    @Column(unique = true, nullable = false, length = 24)
    String Id;
    @Column(length = 256, nullable = false)
    private String name;
    @Column(length = 24, nullable = false)
    private String provinceId;
}
