package com.mtb.booking.mtb.theater.model.dto;

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
public class ScreenDto {

	private Long screenId;

	private String name;

	private TheaterDto threaterDto;

}
