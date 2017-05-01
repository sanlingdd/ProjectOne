package com.linkedin.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkedin.jpa.entity.Location;
import com.linkedin.jpa.repositories.LocationRepository;


@Service
public class LocationServiceImp extends EntityServiceImp<Location> implements LocationService {

	private LocationRepository locationRepository;

	@Autowired
	public LocationServiceImp(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public List<Location> listAll() {
		List<Location> Locations = new ArrayList<Location>();
		locationRepository.findAll().forEach(Locations::add);// fun with Java 8

		return Locations;
	}

	@Override
	public Location getById(Long id) {
		return locationRepository.findOne(id);
	}

	@Override
	public Location saveOrUpdate(Location Location) {
		return locationRepository.save(Location);
	}

	@Override
	public void delete(Long id) {
		locationRepository.delete(id);

	}
}