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
import com.mtb.booking.mtb.theater.entity.Theater;
import com.mtb.booking.mtb.theater.exception.NoSuchTheaterException;
import com.mtb.booking.mtb.theater.mapper.ScreenMapper;
import com.mtb.booking.mtb.theater.mapper.TheaterMapper;
import com.mtb.booking.mtb.theater.model.dto.ScreenDto;
import com.mtb.booking.mtb.theater.model.dto.TheaterDto;
import com.mtb.booking.mtb.theater.model.request.ScreenSaveRequest;
import com.mtb.booking.mtb.theater.model.response.ScreenResponse;
import com.mtb.booking.mtb.theater.repository.ScreenRepository;
import com.mtb.booking.mtb.theater.repository.TheaterRepository;
import com.mtb.booking.mtb.theater.service.ScreenService;

@Service
public class ScreenServiceImpl implements ScreenService {

	@Autowired
	private ScreenRepository screenRepository;

	@Autowired
	private TheaterRepository theaterRepository;

	@Autowired
	private CityRepository cityRepository;

	@Override
	public ScreenResponse saveScreen(ScreenSaveRequest screenSaveRequest) {
		Optional<Screen> optional = Optional.empty();
		if (Objects.nonNull(screenSaveRequest.getScreenId()) && screenSaveRequest.getScreenId() != 0) {
			optional = screenRepository.findById(screenSaveRequest.getScreenId());
		}

		Optional<Theater> optionalTheater = theaterRepository.findById(screenSaveRequest.getTheaterId());

		Theater theater = optionalTheater.orElseThrow(() -> new NoSuchTheaterException(
				String.format("Theater is not available against theaterId %d", screenSaveRequest.getTheaterId())));

		Screen screen = optional.orElse(new Screen());
		screen.setScreenId(screenSaveRequest.getScreenId());
		screen.setName(screenSaveRequest.getName());
		screen.setThreater(theater);

		screen = screenRepository.save(screen);

		ScreenDto screenDto = ScreenMapper.INSTANCE.toScreenDto(screen);
		TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
		theaterDto.setCity(getCityDto(theater.getCityCode()));
		screenDto.setThreaterDto(theaterDto);

		return ScreenResponse.builder().message("Theater save successfully").screenDto(screenDto).build();

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
