package com.vietsoft.model.master;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = String.class)
@Table(name = "country")
@NamedQuery(name = "Country.findAll", query = "SELECT o FROM Country o")
public class Country implements Serializable {

    private static final long serialVersionUID = 7692062378103280397L;

    @Id
    @Column(unique = true, nullable = false, length = 3)
    private String Id;

    @Column(length = 3)
    private String abb2;

    @Column(length = 3)
    private String abb3;

    @Column(length = 256)
    private String continent;

    @Column(length = 256)
    private String name;

    @Column(length = 256)
    private String tel_code;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "countryId", nullable=true)
	@OrderBy("area asc, name asc")
	private List<Province> provinces;
}
