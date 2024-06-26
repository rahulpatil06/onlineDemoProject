package com.altimetrik.Inventory.services.impl;

import com.altimetrik.Inventory.dto.InventoryDto;
import com.altimetrik.Inventory.entity.Inventory;
import com.altimetrik.Inventory.mapper.InventoryMapper;
import com.altimetrik.Inventory.services.InvertoryTestService;
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
public class InvertoryTestServiceImpl implements InvertoryTestService {
    @Autowired
    MongoTemplate mongoTemplate;
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
}
