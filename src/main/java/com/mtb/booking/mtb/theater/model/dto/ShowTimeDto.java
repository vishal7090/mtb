package com.mtb.booking.mtb.theater.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mtb.booking.mtb.master.model.dto.MovieDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ShowTimeDto {

	private Long showTimeId;

	private String name;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date date;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private Date startTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private Date endTime;

	private ScreenDto screen;

	private MovieDto movie;

}
