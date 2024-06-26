package com.altimetrik.category.mapper;

import com.altimetrik.category.dto.CategoryAPIResponse;
import com.altimetrik.category.dto.CategoryDto;
import com.altimetrik.category.dto.InventoryDto;
import com.altimetrik.category.dto.PriceDto;
import com.altimetrik.category.entity.Category;
import com.altimetrik.category.entity.Inventroy;
import com.altimetrik.category.entity.Price;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CatergoryMapper {
    public static CategoryDto maptoCategoryDto(Category category, Price priceDetails, Inventroy inventoryDetails){
        log.info("Inside maptoCategoryDto()....");
        CategoryDto categoryDto = new CategoryDto(
                category.getCategoryId(),
                category.getCategoryType(),
                category.getCategoryName(),
                category.getBrand(),
                category.getDescription(),
                priceDetails,
                inventoryDetails,
                category.getInventoryId(),
                category.getPriceId(),
                category.getAttributes()
        );
        return categoryDto;
    }
    public static Category mapToCategory(CategoryDto categoryDto){
        log.info("Inside mapToCategory() method....");
        Category category = new Category(
                categoryDto.getCategoryId(),
                categoryDto.getCategoryType(),
                categoryDto.getName(),
                categoryDto.getBrand(),
                categoryDto.getDescription(),
                categoryDto.getInventoryId(),
                categoryDto.getPriceId(),
                categoryDto.getAttributeDtoList()

        );
        return category;
    }
    public static CategoryAPIResponse mapToCategoryResponse(CategoryDto categoryDto, PriceDto price, InventoryDto inventroy){
        log.info("Inside mapToCategoryResponse() method....");
        CategoryAPIResponse categoryAPIResponse = new CategoryAPIResponse(categoryDto.getCategoryType(),
                categoryDto.getName(),
                categoryDto.getBrand(),
                categoryDto.getDescription(),
                new Price(price.getCurrency(),price.getAmount()),
                new Inventroy(inventroy.getTotal(),inventroy.getAvailable(),inventroy.getReserved()),
                categoryDto.getAttributeDtoList());
        return categoryAPIResponse;
    }
}
