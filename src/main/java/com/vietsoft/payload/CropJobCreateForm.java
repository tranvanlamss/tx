package com.vietsoft.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CropJobCreateForm {

	String id;

	@NotNull
	String code;

	@NotNull
	String name;

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate startDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	LocalDate endDate;
}
