package com.vietsoft.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role implements Serializable {

  private static final long serialVersionUID = 3390061504123314259L;

  @Id
  @Column(nullable = false, length = 256)
  String name;

  @Column(length = 45)
  String value;

}
