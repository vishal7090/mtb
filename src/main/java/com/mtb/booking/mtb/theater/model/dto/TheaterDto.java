package com.mtb.booking.mtb.theater.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TheaterDto {

	private Long theaterId;

	private String name;

	private String address;

	private CityDto city;

	private Boolean status;

	private List<ScreenDto> screenDtos;

}
