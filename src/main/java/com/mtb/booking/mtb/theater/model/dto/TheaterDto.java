package com.mtb.booking.mtb.theater.model.dto;

import com.mtb.booking.mtb.master.model.dto.CityDto;

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
public class TheaterDto {

	private Long theaterId;

	private String name;

	private String address;

	private CityDto city;

	private Boolean status;

}
