package com.altimetrik.Inventory.controller;

import brave.Tracer;
import com.altimetrik.Inventory.dto.InventoryDto;
import com.altimetrik.Inventory.dto.InventoryResponse;
import com.altimetrik.Inventory.errorHandling.ErrorHandlingControllerAdvice;
import com.altimetrik.Inventory.services.InvertoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.rmi.ServerException;
import java.util.List;


@RestController
@RequestMapping("/inventory-service")
@AllArgsConstructor
@Slf4j
public class InventoryController {
    @Autowired
    InvertoryService invertoryService;

    @Autowired
    Tracer tracer;

    @PostMapping("inventories")
    public ResponseEntity<InventoryDto> addCategory(@RequestBody InventoryDto inventory) {
        log.info("Inside addCategory() method in Controller ....");
        InventoryDto inventoryDtoResponse = invertoryService.addInventory(inventory);
        log.info("Successfully returning inventoryDtoResponse...."+inventoryDtoResponse);
        return new ResponseEntity<>(inventoryDtoResponse, HttpStatus.CREATED);
    }
    @GetMapping("inventories")
    public ResponseEntity<List<InventoryDto>> getDetailedInvetory(@RequestParam(value = "type",defaultValue = "All") String CategoryType,
          @RequestParam(value = "pageNo",defaultValue = "1")int page , @RequestParam(value="pageSize",defaultValue = "10") int size,
           @RequestParam(value="order",defaultValue = "asc")String sortByOrder,@RequestParam(value = "sortBy",defaultValue = "inventory")String sortBy) throws ErrorHandlingControllerAdvice{
        log.info("Inside getDetailedInvetory() method in Controller ....");

        List<InventoryDto> inventoryDtoList = invertoryService.getInventoryDetails(CategoryType,page,size,sortByOrder,sortBy);
        log.info("Successfully returning inventoryDtoList...."+inventoryDtoList);
        return new ResponseEntity<>(inventoryDtoList,HttpStatus.OK);
    }
    @PutMapping("inventories")
    public ResponseEntity<InventoryResponse> updateInventory(@RequestBody @Valid InventoryDto inventory,@RequestHeader String role) throws ErrorHandlingControllerAdvice, AccessDeniedException, ServerException {
        if(!role.equals("Admin")){
            throw new AccessDeniedException("Not Authorised...!!!");
        }
        log.info("Inside updateInventory() method in Controller ....");
        InventoryResponse inventoryDtoResponse = invertoryService.updateInventory(inventory);
        log.info("Successfully returning inventoryDtoResponse...."+inventoryDtoResponse);

        return ResponseEntity.status(HttpStatus.OK).build().ok().body(inventoryDtoResponse);
    }
    @DeleteMapping("inventories")
    public ResponseEntity deleteInventory(@RequestParam String id) throws ErrorHandlingControllerAdvice {
        log.info("Inside deleteInventory() method in Controller ....");
         invertoryService.deleteInvetory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build().ok().body("Deleted Successfully...");
    }
}
