package com.mtb.booking.mtb.theater.model.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mtb.booking.mtb.common.util.Pageable;

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
public class ShowTimeSearchRequest {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date date;

	private Long screenId;

	private Long movieId;

	private Long theaterId;

	private String cityCode;

	private Pageable pageable;
}
