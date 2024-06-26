package com.altimetrik.price.service.impl;

import com.altimetrik.price.dto.Header;
import com.altimetrik.price.dto.PriceDto;
import com.altimetrik.price.dto.PriceResponse;
import com.altimetrik.price.entity.Price;
import com.altimetrik.price.mapper.PriceMapper;
import com.altimetrik.price.repository.PriceRepository;
import com.altimetrik.price.service.PriceService;
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
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepository priceRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    Tracer tracer;
    @Override
    public PriceDto addPrices(PriceDto priceDto) {
        log.info("Inside addPrices() method in Service Layer ....");
        Price price = PriceMapper.mapToPrice(priceDto);
        Price response = priceRepository.save(price);
        PriceDto resposeDto = PriceMapper.maptoPriceDto(response);
        log.info("Successfully Returning PriceResponse DTO .... "+resposeDto);
        return resposeDto;
    }
    @Override
    public List<PriceDto> getPriceList(String categoryType, int page, int size, String sortByOrder, String sortBy) {
        log.info("Inside getPriceList() method in Service Layer ....");
        List<Price> priceList;
        Sort sort;
        Pageable pageable = null;
        if(sortByOrder.equals("desc")){
            sort = Sort.sort(Price.class).by(Price::getAmount).descending();
        }else{
            sort = Sort.sort(Price.class).by(Price::getAmount);
        }
        if(sortBy.equals("price")){
            pageable= PageRequest.of(page-1,size,sort);
        }
        Query query = new Query();
        if(categoryType.equals("All")){
            if(pageable != null){
                priceList = mongoTemplate.find(query.with(pageable),Price.class);
            }else{
                priceList = mongoTemplate.find(query,Price.class);
            }
        }else{
            if(pageable != null){
                query.with(pageable).addCriteria(Criteria.where("categoryType").is(categoryType));
                priceList =  mongoTemplate.find(query,Price.class);
            }else{
                query.addCriteria(Criteria.where("categoryType").is(categoryType));
                priceList =  mongoTemplate.find(query,Price.class);
            }
        }
        List<PriceDto> priceDtoList = new ArrayList<>();
        priceList.forEach(obj -> priceDtoList.add(PriceMapper.maptoPriceDto(obj)));
        log.info("Successfully Returning priceDtoList .... "+priceDtoList);
        if(priceDtoList.isEmpty()){
            throw new NullPointerException("No Such Category and Name Exists...!!!!");
        }
        return priceDtoList;
    }

    @Override
    public PriceResponse updatePrice(PriceDto priceDto) {
        log.info("Inside updatePrice() method in Service Layer ....");
        Price price = PriceMapper.mapToPrice(priceDto);
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("categoryType").is(priceDto.getCategoryType()).and("name").is(priceDto.getName()));
            Price response = mongoTemplate.findOne(query, Price.class);
            response.setCurrency(priceDto.getCurrency());
            response.setAmount(priceDto.getAmount());
            Price updateResponse = mongoTemplate.save(response);
            PriceDto responseDto = PriceMapper.maptoPriceDto(updateResponse);
            log.info("Successfully Returning PriceResponse DTO .... "+responseDto);
            Header header = new Header();
            header.setCode("200");
            header.setMessage("Inventory-service API Updated Data Successfully...");
            header.setTraceId(tracer.currentSpan().context().traceId().toString());
            PriceResponse priceResponse = new PriceResponse();
            priceResponse.setHeader(header);
            priceResponse.setPriceResponseDto(responseDto);
            return priceResponse;
        }catch (NullPointerException ex){
            throw new NullPointerException("No Such Category and Name Exist for the Update...!!!!");
        }
    }

    @Override
    public void deletePrice(String id) {
        log.info("Inside deletePrice() method in Service Layer ....");
        priceRepository.deleteById(id);
        log.info("Successfully Deleted ....");
    }
}
