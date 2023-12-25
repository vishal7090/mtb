package com.mtb.booking.mtb.theater.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtb.booking.mtb.master.entity.City;
import com.mtb.booking.mtb.master.exception.NoSuchCityException;
import com.mtb.booking.mtb.master.mapper.CityMapper;
import com.mtb.booking.mtb.master.model.dto.CityDto;
import com.mtb.booking.mtb.master.repository.CityRepository;
import com.mtb.booking.mtb.theater.entity.Screen;
import com.mtb.booking.mtb.theater.entity.ShowTime;
import com.mtb.booking.mtb.theater.exception.NoSuchTheaterException;
import com.mtb.booking.mtb.theater.mapper.ScreenMapper;
import com.mtb.booking.mtb.theater.mapper.ShowTimeMapper;
import com.mtb.booking.mtb.theater.mapper.TheaterMapper;
import com.mtb.booking.mtb.theater.model.dto.ScreenDto;
import com.mtb.booking.mtb.theater.model.dto.ShowTimeDto;
import com.mtb.booking.mtb.theater.model.dto.TheaterDto;
import com.mtb.booking.mtb.theater.model.request.ShowTimeSaveRequest;
import com.mtb.booking.mtb.theater.model.response.ShowTimeResponse;
import com.mtb.booking.mtb.theater.repository.ScreenRepository;
import com.mtb.booking.mtb.theater.repository.ShowTimeRepository;
import com.mtb.booking.mtb.theater.repository.TheaterRepository;
import com.mtb.booking.mtb.theater.service.ShowTimeService;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {

	@Autowired
	private ShowTimeRepository showTimeRepository;

	@Autowired
	private ScreenRepository screenRepository;

	@Autowired
	private TheaterRepository theaterRepository;

	@Autowired
	private CityRepository cityRepository;

	@Override
	public ShowTimeResponse saveShowTime(ShowTimeSaveRequest showTimeSaveRequest) {
		Optional<ShowTime> optional = Optional.empty();
		if (Objects.nonNull(showTimeSaveRequest.getShowTimeId()) && showTimeSaveRequest.getShowTimeId() != 0) {
			optional = showTimeRepository.findById(showTimeSaveRequest.getShowTimeId());
		}

		Optional<Screen> optionalScreen = screenRepository.findById(showTimeSaveRequest.getScreenId());

		Screen screen = optionalScreen.orElseThrow(() -> new NoSuchTheaterException(
				String.format("Screen is not available against theaterId %d", showTimeSaveRequest.getScreenId())));

		ShowTime showTime = optional.orElse(new ShowTime());
		showTime.setShowTimeId(showTimeSaveRequest.getShowTimeId());

		showTime = showTimeRepository.save(showTime);

		ShowTimeDto showTimeDto = ShowTimeMapper.INSTANCE.toScreenDto(showTime);
		ScreenDto screenDto = ScreenMapper.INSTANCE.toScreenDto(screen);
		showTimeDto.setScreen(screenDto);

		// TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
		// theaterDto.setCity(getCityDto(theater.getCityCode()));
		// screenDto.setThreaterDto(theaterDto);

		return ShowTimeResponse.builder().message("Theater save successfully").showTimeDto(showTimeDto).build();

	}

	// TODO - Feign Client Call for SAGA
	private CityDto getCityDto(String cityCode) {
		Optional<City> optional = cityRepository.findById(cityCode);
		if (optional.isEmpty()) {
			throw new NoSuchCityException(String.format("City is not available against cityCode %s", cityCode));
		}
		City city = optional.get();
		return CityMapper.INSTANCE.toCityDto(city);
	}

}
