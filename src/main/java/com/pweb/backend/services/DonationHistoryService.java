package com.pweb.backend.services;

import com.pweb.backend.dtos.DonationDTO;
import com.pweb.backend.dtos.RequestDTO;
import com.pweb.backend.dtos.SimpleResponseDTO;
import com.pweb.backend.entities.DonationHistoryEntity;
import com.pweb.backend.entities.LocationDetailsEntity;
import com.pweb.backend.repositories.DonationHistoryRepository;
import com.pweb.backend.repositories.LocationDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DonationHistoryService {
    private final DonationHistoryRepository donationHistoryRepository;
    private final LocationDetailsRepository locationDetailsRepository;

    @Autowired
    public DonationHistoryService(DonationHistoryRepository donationHistoryRepository,
                                  LocationDetailsRepository locationDetailsRepository) {
        this.donationHistoryRepository = donationHistoryRepository;
        this.locationDetailsRepository = locationDetailsRepository;
    }

    public SimpleResponseDTO addToHistory(int locationId, List<RequestDTO> fulfilledRequests, String user) {
        donationHistoryRepository.saveAll(fulfilledRequests.stream().map(requestDTO ->
                        new DonationHistoryEntity(locationId, requestDTO.getName(), requestDTO.getCount(), user, Timestamp.from(Instant.now())))
                .collect(Collectors.toList()));
        return new SimpleResponseDTO("", 200);
    }

    public List<DonationDTO> getHistoryForLocation(int locationId) {
        LocationDetailsEntity locationDetails = locationDetailsRepository.findLocationDetailsEntityById(locationId);
        if (Objects.isNull(locationDetails)) {
            log.info("There is no location with id = " + locationId);
            return null;
        }

        return donationHistoryRepository.findAllByLocationIdOrderByDonatedByDesc(locationId).stream()
                .map(DonationDTO::new)
                .collect(Collectors.toList());
    }
}
