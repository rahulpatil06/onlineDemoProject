package Inventory.mapper;


import Inventory.dto.InventoryDto;
import Inventory.entity.Inventory;

public class InventoryMapper {
    public static InventoryDto maptoInventoryDto(Inventory inventory){

        InventoryDto inventoryDto = new InventoryDto(inventory.getInventoryId(),
                inventory.getCategoryType(),
                inventory.getName(),
                inventory.getTotal(),
                inventory.getAvailable(),
                inventory.getReserved());
        return inventoryDto;
    }
//    public static Inventory maptoInventory(InventoryDto inventoryDto){
//
//        Inventory inventory = new Inventory(inventoryDto.getInventoryId(),
//                inventoryDto.getCategoryType(),
//                inventoryDto.getName(),
//                inventoryDto.getTotal(),
//                inventoryDto.getAvailable(),
//                inventoryDto.getReserved());
//        return inventory;
//    }
}
