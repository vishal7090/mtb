package com.mtb.booking.mtb.theater.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mtb.booking.mtb.master.entity.City;
import com.mtb.booking.mtb.master.exception.NoSuchCityException;
import com.mtb.booking.mtb.master.mapper.CityMapper;
import com.mtb.booking.mtb.master.model.dto.CityDto;
import com.mtb.booking.mtb.master.repository.CityRepository;
import com.mtb.booking.mtb.theater.entity.Theater;
import com.mtb.booking.mtb.theater.exception.NoSuchTheaterException;
import com.mtb.booking.mtb.theater.mapper.TheaterMapper;
import com.mtb.booking.mtb.theater.model.dto.TheaterDto;
import com.mtb.booking.mtb.theater.model.request.TheaterChangeStatusRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterSaveRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterSearchRequest;
import com.mtb.booking.mtb.theater.model.response.TheaterResponse;
import com.mtb.booking.mtb.theater.repository.TheaterRepository;
import com.mtb.booking.mtb.theater.service.TheaterService;
import com.mtb.booking.mtb.theater.specification.TheaterSpecification;

@Service
public class TheaterServiceImpl implements TheaterService {

	@Autowired
	private TheaterRepository theaterRepository;

	@Autowired
	private TheaterSpecification theaterSpecification;

	@Autowired
	private CityRepository cityRepository;

	@Override
	public TheaterResponse searchTheaters(TheaterSearchRequest theaterSearchRequest) {

		Page<Theater> page = theaterRepository.findAll(theaterSpecification.searchTheaters(theaterSearchRequest),
				theaterSearchRequest.getPageable().getPageRequest());

		Page<TheaterDto> theaterDtos = page.map(theater -> {
			TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
			CityDto cityDto = getCityDto(theater.getCityCode());
			theaterDto.setCity(cityDto);
			return theaterDto;
		});

		return TheaterResponse.builder().message("Threater search data sucessfully").theaterDtos(theaterDtos).build();
	}

	@Override
	public TheaterResponse getTheater(TheaterRequest theaterRequest) {

		Optional<Theater> optional = Optional.empty();
		if (Objects.nonNull(theaterRequest.getTheaterId()) && theaterRequest.getTheaterId() != 0) {
			optional = theaterRepository.findById(theaterRequest.getTheaterId());
		}

		Theater theater = optional.orElseThrow(() -> new NoSuchTheaterException(
				String.format("Theater is not available against theaterId %d", theaterRequest.getTheaterId())));

		TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
		CityDto cityDto = getCityDto(theater.getCityCode());
		theaterDto.setCity(cityDto);

		return TheaterResponse.builder().message("Threater find data sucessfully").theaterDto(theaterDto).build();
	}

	@Override
	public TheaterResponse saveTheater(TheaterSaveRequest theaterSaveRequest) {
		Optional<Theater> optional = Optional.empty();
		if (Objects.nonNull(theaterSaveRequest.getTheaterId()) && theaterSaveRequest.getTheaterId() != 0) {
			optional = theaterRepository.findById(theaterSaveRequest.getTheaterId());
		}

		CityDto cityDto = getCityDto(theaterSaveRequest.getCityCode());

		boolean isNew = true;
		if (optional.isPresent()) {
			isNew = false;
		}

		Theater theater = optional.orElse(new Theater());
		theater.setTheaterId(theaterSaveRequest.getTheaterId());
		theater.setName(theaterSaveRequest.getName());
		theater.setAddress(theaterSaveRequest.getAddress());
		theater.setCityCode(theaterSaveRequest.getCityCode());

		if (isNew) {
			theater.setStatus(Boolean.FALSE);
		}

		theater = theaterRepository.save(theater);

		TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
		theaterDto.setCity(cityDto);

		return TheaterResponse.builder().message("Theater save successfully").theaterDto(theaterDto).build();
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

	@Override
	public TheaterResponse changeStatusTheater(TheaterChangeStatusRequest theaterChangeStatusRequest) {
		Optional<Theater> optional = Optional.empty();
		if (Objects.nonNull(theaterChangeStatusRequest.getTheaterId())
				&& theaterChangeStatusRequest.getTheaterId() != 0) {
			optional = theaterRepository.findById(theaterChangeStatusRequest.getTheaterId());
		}

		Theater theater = optional.orElseThrow(() -> new NoSuchTheaterException(String
				.format("Theater is not available against theaterId %d", theaterChangeStatusRequest.getTheaterId())));
		boolean status = !theater.getStatus();
		theater.setStatus(status);
		theater = theaterRepository.save(theater);

		CityDto cityDto = getCityDto(theater.getCityCode());
		TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
		theaterDto.setCity(cityDto);

		String statusMessage = status ? "enable" : "disable";

		return TheaterResponse.builder()
				.message(String.format("Theater staus change as %s successfully", statusMessage)).theaterDto(theaterDto)
				.build();
	}

}
