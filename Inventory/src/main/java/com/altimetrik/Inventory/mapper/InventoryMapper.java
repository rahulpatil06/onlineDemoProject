package com.altimetrik.Inventory.mapper;

import com.altimetrik.Inventory.dto.InventoryDto;
import com.altimetrik.Inventory.entity.Inventory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryMapper {
    public static InventoryDto maptoInventoryDto(Inventory inventory){
        log.info("Inside maptoInventoryDto() method in Mapper Layer ....");
        InventoryDto inventoryDto = new InventoryDto(inventory.getInventoryId(),
                inventory.getCategoryType(),
                inventory.getName(),
                inventory.getTotal(),
                inventory.getAvailable(),
                inventory.getReserved());
        return inventoryDto;
    }
    public static Inventory maptoInventory(InventoryDto inventoryDto){
        log.info("Inside maptoInventory() method in Mapper Layer ....");
        Inventory inventory = new Inventory(inventoryDto.getInventoryId(),
                inventoryDto.getCategoryType(),
                inventoryDto.getName(),
                inventoryDto.getTotal(),
                inventoryDto.getAvailable(),
                inventoryDto.getReserved());
        return inventory;
    }
}
