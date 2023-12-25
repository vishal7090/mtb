package com.mtb.booking.mtb.master.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.mtb.booking.mtb.master.entity.Movie;
import com.mtb.booking.mtb.master.model.dto.MovieDto;

@Mapper(componentModel = "spring")
public interface MovieMapper {

	MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

	@Mappings({ @Mapping(target = "movieId", source = "movieId"), @Mapping(target = "name", source = "name"),
			@Mapping(target = "description", source = "description"),
			@Mapping(target = "releaseDate", source = "releaseDate"),
			@Mapping(target = "duration", source = "duration") })
	MovieDto toMovieDto(Movie movie);
}
