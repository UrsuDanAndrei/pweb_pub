package com.pweb.backend.controllers;

import com.pweb.backend.dtos.DonationDTO;
import com.pweb.backend.entities.LocationDetailsEntity;
import com.pweb.backend.services.DonationHistoryService;
import com.pweb.backend.services.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Transactional
@RestController
@RequestMapping("/locations")
public class LocationsController {
    private final LocationsService locationsService;
    private final DonationHistoryService donationHistoryService;

    @Autowired
    public LocationsController(LocationsService locationsService,
                               DonationHistoryService donationHistoryService) {
        this.locationsService = locationsService;
        this.donationHistoryService = donationHistoryService;
    }

    @GetMapping(value = "/viewAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LocationDetailsEntity> getAllLocations() {
        return locationsService.getAllLocationDetails();
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DonationDTO>> getHistoryForLocation(
            @RequestParam("locationId") int locationId
    ) {
        List<DonationDTO> response = donationHistoryService.getHistoryForLocation(locationId);
        return Objects.isNull(response) ? ResponseEntity.badRequest().body(null)
                : ResponseEntity.ok(response);
    }
}
