package com.mtb.booking.mtb.theater.service;

import com.mtb.booking.mtb.theater.model.request.ShowTimeDeleteRequest;
import com.mtb.booking.mtb.theater.model.request.ShowTimeSaveRequest;
import com.mtb.booking.mtb.theater.model.request.ShowTimeSearchRequest;
import com.mtb.booking.mtb.theater.model.response.ShowTimeResponse;

public interface ShowTimeService {

	ShowTimeResponse saveShowTime(ShowTimeSaveRequest showTimeSaveRequest);

	ShowTimeResponse deleteShowTime(ShowTimeDeleteRequest showTimeDeleteRequest);

	ShowTimeResponse searchShowTime(ShowTimeSearchRequest showTimeSearchRequest);

}
