package com.mtb.booking.mtb.theater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtb.booking.mtb.theater.model.request.TheaterChangeStatusRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterSaveRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterSearchRequest;
import com.mtb.booking.mtb.theater.model.response.TheaterResponse;
import com.mtb.booking.mtb.theater.service.TheaterService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/v1/api/theater")
public class TheaterController {

	@Autowired
	private TheaterService theaterService;

	@PostMapping("/searchTheaters")
	public TheaterResponse searchTheaters(@Valid @RequestBody TheaterSearchRequest theaterSearchRequest) {
		return theaterService.searchTheaters(theaterSearchRequest);
	}

	@PostMapping("/getTheater")
	public TheaterResponse getTheater(@Valid @RequestBody TheaterRequest theaterRequest) {
		return theaterService.getTheater(theaterRequest);
	}

	@PostMapping("/saveTheater")
	public TheaterResponse saveTheater(@Valid @RequestBody TheaterSaveRequest theaterSaveRequest) {
		return theaterService.saveTheater(theaterSaveRequest);
	}

	@PostMapping("/changeStatusTheater")
	public TheaterResponse changeStatusTheater(
			@Valid @RequestBody TheaterChangeStatusRequest theaterChangeStatusRequest) {
		return theaterService.changeStatusTheater(theaterChangeStatusRequest);
	}

}
