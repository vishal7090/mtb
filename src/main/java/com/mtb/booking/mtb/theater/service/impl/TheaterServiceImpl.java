package com.mtb.booking.mtb.theater.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.mtb.booking.mtb.theater.exception.NoSuchTheaterException;
import com.mtb.booking.mtb.theater.mapper.ScreenMapper;
import com.mtb.booking.mtb.theater.mapper.ShowTimeMapper;
import com.mtb.booking.mtb.theater.mapper.TheaterMapper;
import com.mtb.booking.mtb.theater.model.dto.ScreenDto;
import com.mtb.booking.mtb.theater.model.dto.ShowTimeDto;
import com.mtb.booking.mtb.theater.model.dto.TheaterDto;
import com.mtb.booking.mtb.theater.model.request.ScreenSearchRequest;
import com.mtb.booking.mtb.theater.model.request.ShowTimeSearchRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterBrowseRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterChangeStatusRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterSaveRequest;
import com.mtb.booking.mtb.theater.model.request.TheaterSearchRequest;
import com.mtb.booking.mtb.theater.model.response.TheaterResponse;
import com.mtb.booking.mtb.theater.repository.ScreenRepository;
import com.mtb.booking.mtb.theater.repository.ShowTimeRepository;
import com.mtb.booking.mtb.theater.repository.TheaterRepository;
import com.mtb.booking.mtb.theater.service.TheaterService;
import com.mtb.booking.mtb.theater.specification.ScreenSpecification;
import com.mtb.booking.mtb.theater.specification.ShowTimeSpecification;
import com.mtb.booking.mtb.theater.specification.TheaterSpecification;

@Service
public class TheaterServiceImpl implements TheaterService {

	@Autowired
	private TheaterRepository theaterRepository;

	@Autowired
	private TheaterSpecification theaterSpecification;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ShowTimeRepository showTimeRepository;

	@Autowired
	private ShowTimeSpecification showTimeSpecification;

	@Autowired
	private ScreenRepository screenRepository;

	@Autowired
	private ScreenSpecification screenSpecification;

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public TheaterResponse browseTheaters(TheaterBrowseRequest theaterBrowseRequest) {

		TheaterSearchRequest theaterSearchRequest = TheaterSearchRequest.builder()
				.cityCode(theaterBrowseRequest.getCityCode()).build();
		Page<Theater> page = theaterRepository.findAll(theaterSpecification.searchTheaters(theaterSearchRequest),
				theaterSearchRequest.getPageable().getPageRequest());

		Page<TheaterDto> theaterDtos = page.map(theater -> {
			TheaterDto theaterDto = TheaterMapper.INSTANCE.toTheaterDto(theater);
			CityDto cityDto = getCityDto(theater.getCityCode());
			theaterDto.setCity(cityDto);

			ScreenSearchRequest screenSearchRequest = ScreenSearchRequest.builder().theaterId(theater.getTheaterId())
					.build();
			// TODO - Use with Pageable with Sort
			List<Screen> screens = screenRepository.findAll(screenSpecification.searchScreens(screenSearchRequest));

			List<ScreenDto> screenDtos = screens.stream().map(screen -> {
				ScreenDto screenDto = ScreenMapper.INSTANCE.toScreenDto(screen);
				List<ShowTimeDto> showTimeDtos = getShowTimes(screen.getScreenId(), theaterBrowseRequest.getDate());
				screenDto.setShowTimeDtos(showTimeDtos);
				return screenDto;
			}).toList();

			theaterDto.setScreenDtos(screenDtos);

			return theaterDto;
		});

		return TheaterResponse.builder().message("Threater browser data sucessfully").theaterDtos(theaterDtos).build();
	}

	private List<ShowTimeDto> getShowTimes(Long screenId, Date date) {
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE).withSort(Sort.by(Order.asc("startTime")));

		ShowTimeSearchRequest showTimeSearchRequest = ShowTimeSearchRequest.builder().screenId(screenId).date(date)
				.build();
		Page<ShowTime> pageShowTimes = showTimeRepository
				.findAll(showTimeSpecification.searchShowTimes(showTimeSearchRequest), pageable);

		return pageShowTimes.map(showTime -> {
			ShowTimeDto showTimeDto = ShowTimeMapper.INSTANCE.toScreenDto(showTime);
			MovieDto movieDto = getMovieDto(showTime.getMovieId());
			showTimeDto.setMovie(movieDto);
			return showTimeDto;
		}).get().toList();
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
