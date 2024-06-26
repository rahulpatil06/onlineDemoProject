package com.altimetrik.Inventory.services.impl;

import com.altimetrik.Inventory.dto.Header;
import com.altimetrik.Inventory.dto.InventoryDto;
import com.altimetrik.Inventory.dto.InventoryResponse;
import com.altimetrik.Inventory.entity.Inventory;
import com.altimetrik.Inventory.mapper.InventoryMapper;
import com.altimetrik.Inventory.repository.InventoryRepository;
import com.altimetrik.Inventory.services.InvertoryService;
import io.micrometer.tracing.Tracer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InvertoryService {

    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    Tracer tracer;

    @Override
    public InventoryDto addInventory(InventoryDto inventoryDto) {
        log.info("Inside addInventory() method in Service Layer ....");
        Inventory inventory = InventoryMapper.maptoInventory(inventoryDto);
        Inventory response = inventoryRepository.save(inventory);
        InventoryDto responseDto = InventoryMapper.maptoInventoryDto(response);
        log.info("Successfully returning responseDto...."+responseDto);
        return responseDto;
    }


    @Override
    public List<InventoryDto> getInventoryDetails(String categoryType, int page, int size, String sortByOrder, String sortBy) {
      log.info("Inside getInventoryDetails() method in Service Layer ....");
        List<Inventory> list;
        Sort sort;
        Pageable pageable = null;
        if(sortByOrder.equals("desc")){
            sort = Sort.sort(Inventory.class).by(Inventory::getAvailable).descending();
        }else{
            sort = Sort.sort(Inventory.class).by(Inventory::getAvailable);
        }
        if(sortBy.equals("inventory")){
            pageable= PageRequest.of(page-1,size,sort);
        }
        Query query = new Query();
        if(categoryType.equals("All")){
            if(pageable !=null){
                list = mongoTemplate.find(query.with(pageable),Inventory.class);
            }else{
                list = mongoTemplate.find(query,Inventory.class);
            }
        }else{
            if(pageable !=null){
                query.with(pageable).addCriteria(Criteria.where("categoryType").is(categoryType));
                list = mongoTemplate.find(query,Inventory.class);
            }else{
                query.addCriteria(Criteria.where("categoryType").is(categoryType));
                list = mongoTemplate.find(query,Inventory.class);
            }
        }
        List<InventoryDto> inventoryDtoList = new ArrayList<>();
        list.forEach(obj -> inventoryDtoList.add(InventoryMapper.maptoInventoryDto(obj)));
        log.info("Successfully returning inventoryDtoList...."+inventoryDtoList);
        if(inventoryDtoList.isEmpty()){
            throw new NullPointerException("No Such Category Exists...!!!!");
        }
        return inventoryDtoList;
    }

    @Override
    public InventoryResponse updateInventory(InventoryDto inventoryDto) {
       log.info("Inside updateInventory() method in Service Layer ....");
        Inventory inventory = InventoryMapper.maptoInventory(inventoryDto);
            try {
                Query query = new Query();
                query.addCriteria(Criteria.where("categoryType").is(inventoryDto.getCategoryType()).and("name").is(inventoryDto.getName()));
                Inventory response = mongoTemplate.findOne(query, Inventory.class);
                if (response.getInventoryId().isBlank() || response.getInventoryId() == null) {
                    throw new NullPointerException("No Such Category Exists to Update.....!!!");
                }
                response.setTotal(inventoryDto.getTotal());
                response.setAvailable(inventoryDto.getAvailable());
                response.setReserved(inventoryDto.getReserved());
                Inventory updateResponse = mongoTemplate.save(response);
                InventoryDto responseDto = InventoryMapper.maptoInventoryDto(updateResponse);
                log.info("Successfully returning responseDto...." + updateResponse);
                Header header = new Header();
                header.setCode("200");
                header.setMessage("Inventory-service API Updated Data Successfully...");
                header.setTraceId(tracer.currentSpan().context().traceId().toString());
                InventoryResponse inventoryResponse = new InventoryResponse();
                inventoryResponse.setHeader(header);
                inventoryResponse.setInventoryResponse(responseDto);
                return inventoryResponse;
            }catch (NullPointerException  ex){
                throw new NullPointerException("No Such Category Exists to Update.....!!!");
        }
    }

    @Override
    public void deleteInvetory(String id) {
        log.info("Inside deleteInventory() method in Service Layer ....");
        inventoryRepository.deleteById(id);
        log.info("Successfully Deleted....");
    }
}
