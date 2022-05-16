package com.pweb.backend.repositories;

import com.pweb.backend.entities.LocationDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDetailsRepository extends JpaRepository<LocationDetailsEntity, Integer> {
    LocationDetailsEntity findLocationDetailsEntityById(int id);
}
