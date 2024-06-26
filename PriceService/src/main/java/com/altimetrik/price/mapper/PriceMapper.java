package com.altimetrik.price.mapper;

import com.altimetrik.price.dto.PriceDto;
import com.altimetrik.price.entity.Price;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceMapper {
    public static PriceDto maptoPriceDto(Price price){
        log.info("Inside maptoPriceDto() method in Mapper Layer ....");
        PriceDto priceDto = new PriceDto(price.getPriceId(),
                price.getCategoryType(), price.getName(), price.getCurrency(), price.getAmount());
        return priceDto;
    }
    public static Price mapToPrice(PriceDto priceDto){
        log.info("Inside mapToPrice() method in Mapper Layer ....");
        Price price = new Price(priceDto.getPriceId(), priceDto.getCategoryType(), priceDto.getName(),
                priceDto.getCurrency(), priceDto.getAmount());
        return price;
    }
}
