package com.mtb.booking.mtb.theater.service;

import com.mtb.booking.mtb.theater.model.request.ShowTimeSaveRequest;
import com.mtb.booking.mtb.theater.model.response.ShowTimeResponse;

public interface ShowTimeService {

	ShowTimeResponse saveShowTime(ShowTimeSaveRequest showTimeSaveRequest);

}
