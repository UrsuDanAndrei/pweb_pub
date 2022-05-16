package com.pweb.backend.dtos;

import com.pweb.backend.entities.ItemLocationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String name;
    private int count;
    private int priority;

    @JsonIgnore
    @Override
    public String toString() {
        return "{" + name + ", " + count + ", " + priority + "}";
    }

    @JsonIgnore
    public ItemLocationEntity getItemLocationEntity(int locationId) {
        return new ItemLocationEntity(locationId, name, count, priority);
    }
}
