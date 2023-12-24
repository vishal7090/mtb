package com.mtb.booking.mtb.theater.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.mtb.booking.mtb.theater.entity.Screen;
import com.mtb.booking.mtb.theater.model.dto.ScreenDto;

@Mapper(componentModel = "spring")
public interface ScreenMapper {

	ScreenMapper INSTANCE = Mappers.getMapper(ScreenMapper.class);

	@Mappings({ @Mapping(target = "screenId", source = "screenId"), @Mapping(target = "name", source = "name") })
	ScreenDto toScreenDto(Screen screen);
}
