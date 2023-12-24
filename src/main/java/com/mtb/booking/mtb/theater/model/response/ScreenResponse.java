package com.mtb.booking.mtb.theater.model.response;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mtb.booking.mtb.theater.model.dto.ScreenDto;

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
public class ScreenResponse {

	@Builder.Default
	private int status = 1;

	private String message;

	private ScreenDto screenDto;

	private Page<ScreenDto> screenDtos;

}
