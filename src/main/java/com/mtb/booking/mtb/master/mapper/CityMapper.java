package com.mtb.booking.mtb.master.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.mtb.booking.mtb.master.entity.City;
import com.mtb.booking.mtb.master.model.dto.CityDto;

@Mapper(componentModel = "spring")
public interface CityMapper {

	CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

	@Mappings({ @Mapping(target = "code", source = "code"), @Mapping(target = "name", source = "name") })
	CityDto toCityDto(City city);
}
