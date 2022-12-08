package com.vietsoft.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "user_role")
public class UserRole implements Serializable {

  private static final long serialVersionUID = 3390061504123314259L;

  @Id
  @GeneratedValue(generator = "objectid-generator")
  @GenericGenerator(name = "objectid-generator", strategy = "com.vietsoft.common.ObjectIDGenerator")
  @Column(unique = true, nullable = false, length = 24)
  String id;

  @Column(name = "user_id", nullable = false)
  String userId;

  @Column(length = 45)
  String role;

}
