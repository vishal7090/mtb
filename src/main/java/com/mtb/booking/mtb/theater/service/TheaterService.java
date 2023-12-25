package com.mtb.booking.mtb.theater.service;

import com.mtb.booking.mtb.theater.model.request.TheaterBrowseRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterChangeStatusRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterSaveRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterSearchRequest;
import com.mtb.booking.mtb.theater.model.response.TheaterResponse;

public interface TheaterService {

	TheaterResponse searchTheaters(TheaterSearchRequest theaterSearchRequest);

	TheaterResponse getTheater(TheaterRequest theaterRequest);

	TheaterResponse saveTheater(TheaterSaveRequest theaterSaveRequest);

	TheaterResponse changeStatusTheater(TheaterChangeStatusRequest theaterChangeStatusRequest);

	TheaterResponse browseTheaters(TheaterBrowseRequest theaterBrowseRequest);

}
