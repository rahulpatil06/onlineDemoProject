package Inventory.services;

import Inventory.dto.InventoryDto;

import java.util.List;

public interface InvertoryTestService {
    public List<InventoryDto> getInventoryDetails(String categoryType, int page, int size, String sortByOrder, String sortBy) ;

}
