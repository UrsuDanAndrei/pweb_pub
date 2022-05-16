package com.pweb.backend.repositories;

import com.pweb.backend.entities.ItemLocationEntity;
import com.pweb.backend.entities.ItemLocationEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemLocationRepository extends JpaRepository<ItemLocationEntity, ItemLocationEntityPK> {
    List<ItemLocationEntity> findAllByLocationIdOrderByPriorityDesc(int locationId);
    ItemLocationEntity findByLocationIdAndItemName(int locationId, String itemName);
    List<ItemLocationEntity> findAllByLocationIdAndItemNameIsIn(int locationId, List<String> itemNames);
}
