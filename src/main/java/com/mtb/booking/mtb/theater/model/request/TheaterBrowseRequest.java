package com.mtb.booking.mtb.theater.model.request;

import java.util.Date;

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
public class TheaterBrowseRequest {

	private String cityCode;

	private Date date;

	private Pageable pageable;
}
