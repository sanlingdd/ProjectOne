package com.linkedin.jpa.service;

import java.util.List;

import com.linkedin.jpa.entity.Location;

public interface LocationService extends EntityService<Location>{
	List<Location> listAll();

	Location getById(Long id);

	Location saveOrUpdate(Location location);

	void delete(Long id);
}
