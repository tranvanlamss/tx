package com.vietsoft.model.master;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Table(name = "province")
public class Province implements Serializable {
    private static final long serialVersionUID = -4605239782527819479L;

    @Id
    @GeneratedValue(generator = "objectid-generator")
    @GenericGenerator(name = "objectid-generator", strategy = "com.vietsoft.common.ObjectIDGenerator")
    @Column(unique = true, nullable = false, length = 24)
    String Id;

	@Column(nullable=false, length=64)
	String name;

    @Column(nullable = false, length = 64)
    String area;

    @Column(nullable = false, length = 3)
    private String countryId;

    public Province() {
    }

    public Province(String Id, String name, String area, String countryId) {
        this.Id = Id;
        this.name = name;
        this.area = area;
        this.countryId = countryId;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public Province Id(String Id) {
        this.Id = Id;
        return this;
    }

    public Province name(String name) {
        this.name = name;
        return this;
    }

    public Province area(String area) {
        this.area = area;
        return this;
    }

    public Province countryId(String countryId) {
        this.countryId = countryId;
        return this;
    }

    @Override
    public String toString() {
        return "Province{" +
                "Id='" + Id + '\'' +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", countryId='" + countryId + '\'' +
                '}';
    }
}