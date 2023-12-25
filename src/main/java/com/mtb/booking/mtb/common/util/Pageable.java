package com.mtb.booking.mtb.common.util;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {

	private int page;

	@Builder.Default
	private int size = 20;

	private List<String> sort;

	@Hidden
	public PageRequest getPageRequest() {

		if (size == -1) {
			size = Integer.MAX_VALUE;
		}
		PageRequest pageRequest = PageRequest.of(page, size);

		// TODO - write order logic

		return pageRequest;

	}

}
