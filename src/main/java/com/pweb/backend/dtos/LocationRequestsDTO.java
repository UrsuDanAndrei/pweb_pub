package com.pweb.backend.dtos;

import com.pweb.backend.entities.ItemLocationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LocationRequestsDTO {
    private int locationId;
    private List<RequestDTO> requests;

    public LocationRequestsDTO(int locationId, List<ItemLocationEntity> items) {
        this.locationId = locationId;
        requests = items.stream()
                .map(item -> new RequestDTO(item.getItemName(), item.getCount(), item.getPriority()))
                .collect(Collectors.toList());
    }
}
