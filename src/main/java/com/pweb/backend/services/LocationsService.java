package com.pweb.backend.services;

import com.pweb.backend.entities.LocationDetailsEntity;
import com.pweb.backend.repositories.LocationDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LocationsService {
    private final LocationDetailsRepository locationDetailsRepository;

    @Autowired
    public LocationsService(LocationDetailsRepository locationDetailsRepository) {
        this.locationDetailsRepository = locationDetailsRepository;
    }

    public List<LocationDetailsEntity> getAllLocationDetails() {
        List<LocationDetailsEntity> temp = locationDetailsRepository.findAll();

        log.info(temp.toString());

        return temp;
    }
}
