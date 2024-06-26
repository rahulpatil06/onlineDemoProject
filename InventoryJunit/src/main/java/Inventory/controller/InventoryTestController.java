package Inventory.controller;

import Inventory.dto.InventoryDto;
import Inventory.services.InvertoryTestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/inventoryTest")
@AllArgsConstructor
public class InventoryTestController {
    @Autowired
    InvertoryTestService invertoryService;

    @GetMapping("inventoryDetails")
    public ResponseEntity<List<InventoryDto>> getDetailedInvetory(@RequestParam(value = "type",defaultValue = "All") String CategoryType,
                                                                  @RequestParam(value = "pageNo",defaultValue = "1")int page , @RequestParam(value="pageSize",defaultValue = "10") int size,
                                                                  @RequestParam(value="order",defaultValue = "asc")String sortByOrder, @RequestParam(value = "sortBy",defaultValue = "inventory")String sortBy){
        List<InventoryDto> inventoryDtoList = invertoryService.getInventoryDetails(CategoryType,page,size,sortByOrder,sortBy);
        return new ResponseEntity<>(inventoryDtoList, HttpStatus.OK);
    }
}
