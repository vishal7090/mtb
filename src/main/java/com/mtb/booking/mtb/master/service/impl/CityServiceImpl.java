package com.mtb.booking.mtb.master.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtb.booking.mtb.master.repository.CityRepository;
import com.mtb.booking.mtb.master.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;
	
	
	
}
