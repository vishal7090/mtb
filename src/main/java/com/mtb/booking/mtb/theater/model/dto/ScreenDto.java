package com.mtb.booking.mtb.theater.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
public class ScreenDto {

	private Long screenId;

	private String name;

	private TheaterDto threaterDto;

	private List<ShowTimeDto> showTimeDtos;

}
