package com.mtb.booking.mtb.theater.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.mtb.booking.mtb.master.entity.City;
import com.mtb.booking.mtb.master.entity.Movie;
import com.mtb.booking.mtb.master.exception.NoSuchCityException;
import com.mtb.booking.mtb.master.exception.NoSuchMovieException;
import com.mtb.booking.mtb.master.mapper.CityMapper;
import com.mtb.booking.mtb.master.mapper.MovieMapper;
import com.mtb.booking.mtb.master.model.dto.CityDto;
import com.mtb.booking.mtb.master.model.dto.MovieDto;
import com.mtb.booking.mtb.master.repository.CityRepository;
import com.mtb.booking.mtb.master.repository.MovieRepository;
import com.mtb.booking.mtb.theater.entity.Screen;
import com.mtb.booking.mtb.theater.entity.ShowTime;
import com.mtb.booking.mtb.theater.entity.Theater;
import com.mtb.booking.mtb.theater.exception.NoSuchScreenException;
import com.mtb.booking.mtb.theater.exception.NoSuchShowTimeException;
import com.mtb.booking.mtb.theater.mapper.ScreenMapper;
import com.mtb.booking.mtb.theater.mapper.ShowTimeMapper;
import com.mtb.booking.mtb.theater.mapper.TheaterMapper;
import com.mtb.booking.mtb.theater.model.dto.ScreenDto;
import com.mtb.booking.mtb.theater.model.dto.ShowTimeDto;
import com.mtb.booking.mtb.theater.model.dto.TheaterDto;
import com.mtb.booking.mtb.theater.model.request.ShowTimeDeleteRequest;
import com.mtb.booking.mtb.theater.model.request.ShowTimeSaveRequest;
import com.mtb.booking.mtb.theater.model.request.ShowTimeSearchRequest;
import com.mtb.booking.mtb.theater.model.response.ShowTimeResponse;
import com.mtb.booking.mtb.theater.repository.ScreenRepository;
import com.mtb.booking.mtb.theater.repository.ShowTimeRepository;
import com.mtb.booking.mtb.theater.service.ShowTimeService;
import com.mtb.booking.mtb.theater.specification.ShowTimeSpecification;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {

	@Autowired
	private ShowTimeRepository showTimeRepository;

	@Autowired
	private ScreenRepository screenRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ShowTimeSpecification showTimeSpecification;

	@Override
	public ShowTimeResponse searchShowTime(ShowTimeSearchRequest showTimeSearchRequest) {

		Pageable pageable = showTimeSearchRequest.getPageable().getPageRequest()
				.withSort(Sort.by(Order.asc("startTime")));
		Page<ShowTime> page = showTimeRepository.findAll(showTimeSpecification.searchShowTimes(showTimeSearchRequest),
				pageable);

		Page<ShowTimeDto> showTimeDtos = page.map(showTime -> {
			ShowTimeDto showTimeDto = ShowTimeMapper.INSTANCE.toScreenDto(showTime);
			ScreenDto screenDto = ScreenMapper.INSTANCE.toScreenDto(showTime.getScreen());
			Theater theater = showTime.getScreen().getThreater();
			TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
			theaterDto.setCity(getCityDto(theater.getCityCode()));
			screenDto.setThreaterDto(theaterDto);
			showTimeDto.setScreen(screenDto);
			MovieDto movieDto = getMovieDto(showTime.getMovieId());
			showTimeDto.setMovie(movieDto);

			return showTimeDto;
		});

		return ShowTimeResponse.builder().message("ShowTime search successfully").showTimeDtos(showTimeDtos).build();
	}

	@Override
	public ShowTimeResponse saveShowTime(ShowTimeSaveRequest showTimeSaveRequest) {
		Optional<ShowTime> optional = Optional.empty();
		if (Objects.nonNull(showTimeSaveRequest.getShowTimeId()) && showTimeSaveRequest.getShowTimeId() != 0) {
			optional = showTimeRepository.findById(showTimeSaveRequest.getShowTimeId());
		}

		Optional<Screen> optionalScreen = screenRepository.findById(showTimeSaveRequest.getScreenId());

		Screen screen = optionalScreen.orElseThrow(() -> new NoSuchScreenException(
				String.format("Screen is not available against screenId %d", showTimeSaveRequest.getScreenId())));

		MovieDto movieDto = getMovieDto(showTimeSaveRequest.getMovieId()); // Validation of Movie

		// TODO - Time overlapping Validation with Same Screen

		ShowTime showTime = optional.orElse(new ShowTime());
		showTime.setShowTimeId(showTimeSaveRequest.getShowTimeId());
		showTime.setDate(showTimeSaveRequest.getDate());
		showTime.setStartTime(showTimeSaveRequest.getStartTime());
		showTime.setEndTime(showTimeSaveRequest.getEndTime());
		showTime.setScreen(screen);
		showTime.setMovieId(showTimeSaveRequest.getMovieId());

		showTime = showTimeRepository.save(showTime);

		ShowTimeDto showTimeDto = ShowTimeMapper.INSTANCE.toScreenDto(showTime);
		ScreenDto screenDto = ScreenMapper.INSTANCE.toScreenDto(screen);
		Theater theater = screen.getThreater();
		TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
		theaterDto.setCity(getCityDto(theater.getCityCode()));
		screenDto.setThreaterDto(theaterDto);
		showTimeDto.setScreen(screenDto);
		showTimeDto.setMovie(movieDto);

		return ShowTimeResponse.builder().message("ShowTime save successfully").showTimeDto(showTimeDto).build();

	}

	@Override
	public ShowTimeResponse deleteShowTime(ShowTimeDeleteRequest showTimeDeleteRequest) {

		Optional<ShowTime> optional = Optional.empty();
		if (Objects.nonNull(showTimeDeleteRequest.getShowTimeId()) && showTimeDeleteRequest.getShowTimeId() != 0) {
			optional = showTimeRepository.findById(showTimeDeleteRequest.getShowTimeId());
		}

		ShowTime showTime = optional.orElseThrow(() -> new NoSuchShowTimeException(String
				.format("ShowTIme is not available against showTimeId %d", showTimeDeleteRequest.getShowTimeId())));

		showTimeRepository.delete(showTime);

		return ShowTimeResponse.builder().message("ShowTime delete successfully").build();
	}

	// TODO - Feign Client Call for SAGA
	private MovieDto getMovieDto(Long movieId) {
		Optional<Movie> optional = movieRepository.findById(movieId);
		if (optional.isEmpty()) {
			throw new NoSuchMovieException(String.format("Movie is not available against movieId %d", movieId));
		}
		Movie movie = optional.get();
		return MovieMapper.INSTANCE.toMovieDto(movie);
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
