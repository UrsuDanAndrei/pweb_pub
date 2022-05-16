package com.pweb.backend.services;

import com.pweb.backend.dtos.LocationRequestsDTO;
import com.pweb.backend.dtos.RequestDTO;
import com.pweb.backend.dtos.SimpleResponseDTO;
import com.pweb.backend.entities.ItemLocationEntity;
import com.pweb.backend.entities.LocationDetailsEntity;
import com.pweb.backend.repositories.ItemLocationRepository;
import com.pweb.backend.repositories.LocationDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestsService {
    private final ItemLocationRepository itemLocationRepository;
    private final LocationDetailsRepository locationDetailsRepository;

    @Autowired
    public RequestsService(ItemLocationRepository itemLocationRepository,
                           LocationDetailsRepository locationDetailsRepository) {
        this.itemLocationRepository = itemLocationRepository;
        this.locationDetailsRepository = locationDetailsRepository;
    }

    public LocationRequestsDTO getRequestsByLocation(int locationId) {
        List<ItemLocationEntity> temp = itemLocationRepository.findAllByLocationIdOrderByPriorityDesc(locationId);
        if (Objects.isNull(temp) || temp.isEmpty()) {
            log.info("No requests for location " + locationId);
            return new LocationRequestsDTO(locationId, new ArrayList<>());
        }

        LocationRequestsDTO ret = new LocationRequestsDTO(locationId, temp);
        log.info("Requests for location " + locationId  + ": "
                + ret.getRequests().stream().map(RequestDTO::getName).collect(Collectors.joining(", ")));
        return ret;
    }

    public SimpleResponseDTO saveNewRequest(int locationId, RequestDTO request) {
        ItemLocationEntity oldRequest = itemLocationRepository.findByLocationIdAndItemName(locationId, request.getName());
        if (Objects.nonNull(oldRequest)) {
            log.info("Existing request for " + locationId + " found");
            request.setCount(request.getCount() + oldRequest.getCount());
            if (oldRequest.getPriority() > request.getPriority()) {
                request.setPriority(oldRequest.getPriority());
            }
        }

        LocationDetailsEntity locationDetails = locationDetailsRepository.findLocationDetailsEntityById(locationId);
        if (Objects.isNull(locationDetails)) {
            log.info("There is no location with id = " + locationId);
            return new SimpleResponseDTO("There is no location with id = " + locationId, 400);
        }

        ItemLocationEntity toSave = new ItemLocationEntity(locationId, request.getName(), request.getCount(), request.getPriority());
        log.info("Saving: " + request + " for location " + locationId);
        itemLocationRepository.save(toSave);

        return new SimpleResponseDTO("", 200);
    }

    public SimpleResponseDTO markReceived(int locationId, List<RequestDTO> fulfilledRequests) {
        List<ItemLocationEntity> filteredItemLocationList = itemLocationRepository.findAllByLocationIdAndItemNameIsIn(
                locationId,
                fulfilledRequests.stream().map(RequestDTO::getName).collect(Collectors.toList()));

        Map<String, RequestDTO> affectedRequests = new LocationRequestsDTO(locationId, filteredItemLocationList)
                .getRequests().stream().collect(Collectors.toMap(
                        RequestDTO::getName,
                        Function.identity()
                ));

        List<ItemLocationEntity> toSave = new ArrayList<>();
        List<ItemLocationEntity> toDelete = new ArrayList<>();

        for (RequestDTO request : fulfilledRequests) {
            if (!affectedRequests.containsKey(request.getName())) {
                log.info("Location with id=" + locationId + " has no request for " + request.getName());
                return new SimpleResponseDTO("Location with id=" + locationId + " has no request for " + request.getName(), 400);
            }

            RequestDTO current = affectedRequests.get(request.getName());
            if (request.getCount() >= current.getCount()) {
                current.setCount(0);
                toDelete.add(current.getItemLocationEntity(locationId));

                log.info("Deleting request for item=" + current.getName() + " (location=" + locationId + ")");
            } else {
                current.setCount(current.getCount() - request.getCount());
                toSave.add(current.getItemLocationEntity(locationId));

                log.info("Updating count for item=" + current.getName() + " (location=" + locationId + ")");
            }
        }

        if (!toSave.isEmpty()) {
            itemLocationRepository.saveAll(toSave);
        }

        if (!toDelete.isEmpty()) {
            itemLocationRepository.deleteAll(toDelete);
        }

        return new SimpleResponseDTO("", 200);
    }
}
