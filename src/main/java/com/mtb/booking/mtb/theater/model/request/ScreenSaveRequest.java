package com.mtb.booking.mtb.theater.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ScreenSaveRequest {

	private Long screenId;

	@NotEmpty(message = "Screen Name should not null or empty")
	private String name;

	@NotNull(message = "Theater Id should not null or empty")
	@Positive(message = "Theater Id must be valid value")
	private Long theaterId;
}
