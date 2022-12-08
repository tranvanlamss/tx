package com.vietsoft.model.market;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id", scope=String.class)
@Table(name="order_detail")
public class OrderDetail implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "objectid-generator") @GenericGenerator(name = "objectid-generator", strategy = "com.vietsoft.common.ObjectIDGenerator")
    @Column(unique=true, nullable=false, length=24)
    String id;

    @Column(length=256)
    String orderId;

    @Column(length=256)
    String productId;
}
