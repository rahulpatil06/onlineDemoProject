package com.altimetrik.Inventory.services;

import com.altimetrik.Inventory.dto.InventoryDto;
import com.altimetrik.Inventory.dto.InventoryResponse;
import com.altimetrik.Inventory.errorHandling.ErrorHandlingControllerAdvice;

import java.util.List;

public interface InvertoryService {
    InventoryDto addInventory(InventoryDto inventory);
    List<InventoryDto> getInventoryDetails(String categoryType, int page, int size, String sortByOrder, String sortBy);
    InventoryResponse updateInventory(InventoryDto inventory) throws ErrorHandlingControllerAdvice;

    void deleteInvetory(String id);
}
