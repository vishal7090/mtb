package com.mtb.booking.mtb.theater.model.request;

import jakarta.validation.constraints.NotEmpty;
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
public class TheaterSaveRequest {

	private Long theaterId;

	@NotEmpty(message = "Theater Id should not null or empty")
	private String name;

	@NotEmpty(message = "Address should not null or empty")
	private String address;

	@NotEmpty(message = "City Code should not null or empty")
	private String cityCode;

}
