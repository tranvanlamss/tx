package com.vietsoft.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "crop_job_training")
public class CropJob implements Serializable {

    @Id
    @GeneratedValue(generator = "objectid-generator")
    @GenericGenerator(name = "objectid-generator", strategy = "com.vietsoft.common.ObjectIDGenerator")
    @Column(unique = true, nullable = false, length = 24)
    private String id;

    @Column(nullable = false, length = 100, unique = true)
    private String code;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_by", length = 24)
    private String updatedBy;

    @Column(name = "created_by", nullable = false, length = 24)
    private String createdBy;
}
