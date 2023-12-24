package com.mtb.booking.mtb.theater.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.mtb.booking.mtb.theater.entity.Theater;
import com.mtb.booking.mtb.theater.model.dto.TheaterDto;

@Mapper(componentModel = "spring")
public interface TheaterMapper {

	TheaterMapper INSTANCE = Mappers.getMapper(TheaterMapper.class);

	@Mappings({ @Mapping(target = "theaterId", source = "theaterId"), @Mapping(target = "name", source = "name"),
			@Mapping(target = "address", source = "address"), @Mapping(target = "status", source = "status") })
	TheaterDto toTheaterDto(Theater theater);
}
