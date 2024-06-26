package com.altimetrik.Inventory.repository;

import com.altimetrik.Inventory.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface InventoryRepository extends MongoRepository<Inventory, String> {

}
