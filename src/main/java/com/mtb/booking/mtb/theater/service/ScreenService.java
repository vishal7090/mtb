package com.mtb.booking.mtb.theater.service;

import com.mtb.booking.mtb.theater.model.request.ScreenSaveRequest;
import com.mtb.booking.mtb.theater.model.response.ScreenResponse;

public interface ScreenService {

	ScreenResponse saveScreen(ScreenSaveRequest screenSaveRequest);

}
