package com.pweb.backend.controllers;

import com.pweb.backend.dtos.SimpleResponseDTO;
import com.pweb.backend.dtos.LocationRequestsDTO;
import com.pweb.backend.dtos.RequestDTO;
import com.pweb.backend.services.DonationHistoryService;
import com.pweb.backend.services.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Transactional
@RestController
@RequestMapping("/requests")
public class RequestsController {
    private final RequestsService requestsService;
    private final DonationHistoryService donationHistoryService;

    @Autowired
    public RequestsController(RequestsService requestsService,
                              DonationHistoryService donationHistoryService) {
        this.requestsService = requestsService;
        this.donationHistoryService = donationHistoryService;
    }

    @GetMapping(value = "/view",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public LocationRequestsDTO getRequestsForLocation(
            @RequestParam("locationId") int locationId
    ) {
        return requestsService.getRequestsByLocation(locationId);
    }

    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleResponseDTO> addRequestForLocation(
            @RequestParam("locationId") int locationId,
            @RequestBody RequestDTO request
    ) {
        SimpleResponseDTO response = requestsService.saveNewRequest(locationId, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping(value = "/received",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SimpleResponseDTO> markReceivedItems(
            @RequestParam("locationId") int locationId,
            @RequestBody List<RequestDTO> fulfilledRequests
    ) {
        SimpleResponseDTO response = requestsService.markReceived(locationId, fulfilledRequests);

        if (response.getStatus() != 200) {
            return ResponseEntity.status(response.getStatus()).body(response);
        }

        // add actual user to logs
        response = donationHistoryService.addToHistory(locationId, fulfilledRequests, "tester@test.com");
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
