package com.mtb.booking.mtb.theater.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.mtb.booking.mtb.theater.entity.ShowTime;
import com.mtb.booking.mtb.theater.model.dto.ShowTimeDto;

@Mapper(componentModel = "spring")
public interface ShowTimeMapper {

	ShowTimeMapper INSTANCE = Mappers.getMapper(ShowTimeMapper.class);

	@Mappings({ @Mapping(target = "showTimeId", source = "showTimeId"), @Mapping(target = "name", source = "name"),
			@Mapping(target = "date", source = "date"), @Mapping(target = "startTime", source = "startTime"),
			@Mapping(target = "endTime", source = "endTime") })
	ShowTimeDto toScreenDto(ShowTime showTime);
}
