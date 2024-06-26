package com.altimetrik.price.service;

import com.altimetrik.price.dto.PriceDto;
import com.altimetrik.price.dto.PriceResponse;

import java.util.List;

public interface PriceService {
    PriceDto addPrices(PriceDto priceDto);
    List<PriceDto> getPriceList(String categoryType, int page, int size, String sortByOrder, String sortBy);
    PriceResponse updatePrice(PriceDto priceDto);

    void deletePrice(String id);
}
