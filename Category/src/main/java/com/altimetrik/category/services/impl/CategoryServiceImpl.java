package com.altimetrik.category.services.impl;

import brave.Tracer;
import com.altimetrik.category.dto.*;
import com.altimetrik.category.entity.Category;
import com.altimetrik.category.entity.Inventroy;
import com.altimetrik.category.entity.Price;
import com.altimetrik.category.errorHandling.ErrorHandlingControllerAdvice;
import com.altimetrik.category.mapper.CatergoryMapper;
import com.altimetrik.category.repository.CategoryRespository;
import com.altimetrik.category.services.CategoryService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.reactive.function.client.WebClient;
import util.CategoryUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRespository categoryRespository;
    private WebClient webClient;

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    Tracer tracer;

    @Autowired
    CategoryResponse catResponse ;
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultAddMethod")
    @Override
    public CategoryResponse addCategory(CategoryDto categoryDto) throws IOException, ErrorHandlingControllerAdvice {
        log.info("Inside addCategory() in Service Layer ....");
        InventoryDto inventoryDto = new InventoryDto(categoryDto.getInventoryId(), categoryDto.getCategoryType(), categoryDto.getName(),
                categoryDto.getInventory().getTotal(), categoryDto.getInventory().getAvailable(), categoryDto.getInventory().getReserved());

        PriceDto priceDto = new PriceDto(categoryDto.getPriceId(), categoryDto.getCategoryType(),
                categoryDto.getName(), categoryDto.getPrice().getCurrency(), categoryDto.getPrice().getAmount());

            PriceDto priceresponse = webClient.post().uri(CategoryUtil.PRICE_SERVICE)
                    .bodyValue(priceDto).retrieve().bodyToMono(PriceDto.class).block();

            InventoryDto inventoryResponse = webClient.post().uri(CategoryUtil.INVENTORY_SERVICE)
                    .bodyValue(inventoryDto).retrieve().bodyToMono(InventoryDto.class).block();
        try {
            categoryDto.setPriceId(priceresponse.getPriceId());
            categoryDto.setInventoryId(inventoryResponse.getInventoryId());
            Category category = CatergoryMapper.mapToCategory(categoryDto);
            Category savedCategory = categoryRespository.save(category);
            CategoryDto savedDto = CatergoryMapper.maptoCategoryDto(savedCategory, new Price(priceresponse.getCurrency(), priceresponse.getAmount()),
                    new Inventroy(inventoryResponse.getTotal(), inventoryResponse.getAvailable(), inventoryResponse.getReserved()));
            CategoryAPIResponse categoryAPIResponse = CatergoryMapper.mapToCategoryResponse(savedDto, priceresponse, inventoryResponse);
            log.info("Successfully returning categoryAPIResponse from addCategory Method...."+categoryAPIResponse);
            Header header = new Header();
            header.setMessage("Category Service API Added Data successfully..");
            header.setCode("201");
            header.setTraceId(tracer.currentSpan().context().traceIdString());
            List<CategoryAPIResponse> categoryAPIResponseList = new ArrayList<>();
            categoryAPIResponseList.add(categoryAPIResponse);
            catResponse.setCategoryAPIResponse(categoryAPIResponseList);
            catResponse.setHeader(header);
            return catResponse;
        } catch (Exception e) {
            throw new ErrorHandlingControllerAdvice();
        }
    }

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultCategory")
    @Override
    public CategoryResponse getCategoryDetails(String categoryType, int page, int size, String sortByOrder, String sortBy) throws ErrorHandlingControllerAdvice {
        log.info("Inside getCategoryDetails() in Service Layer ....");

        String val = null;
        List<Category> list;
        if (categoryType.equals("All")) {
            list = mongoTemplate.findAll(Category.class);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("CategoryType").is(categoryType));
            list = mongoTemplate.find(query, Category.class);
        }
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : list) {
            categoryDtoList.add(CatergoryMapper.maptoCategoryDto(category, null, null));
        }
        List<CategoryAPIResponse> categoryResponseList = new ArrayList<>();
        HashMap<String, InventoryDto> inventroyHashMap = new HashMap<>();
        HashMap<String, PriceDto> priceHashMap = new HashMap<>();
        List<InventoryDto> inventoryDtoList = webClient.get().uri(CategoryUtil.INVENTORY_SERVICE+"?type=" + categoryType + "&pageNo=" + page + "&pageSize=" + size + "&order=" + sortByOrder + "&sortBy=" + sortBy)
                .retrieve().bodyToFlux(InventoryDto.class).collectList().block();
        inventoryDtoList.forEach(a -> inventroyHashMap.put(a.getInventoryId(), a));
        List<PriceDto> priceList = webClient.get().uri(CategoryUtil.PRICE_SERVICE+"?type=" + categoryType + "&pageNo=" + page + "&pageSize=" + size + "&order=" + sortByOrder + "&sortBy=" + sortBy)
                .retrieve().bodyToFlux(PriceDto.class).collectList().block();
        priceList.forEach(p -> priceHashMap.put(p.getPriceId(), p));

        if (sortBy.equals("inventory")) {
            HashMap<String, CategoryDto> categoryDtoHashMap = new HashMap<>();
            categoryDtoList.forEach(cobj -> categoryDtoHashMap.put(cobj.getInventoryId(), cobj));
            inventoryDtoList.forEach(l -> categoryResponseList.add(CatergoryMapper.mapToCategoryResponse(categoryDtoHashMap.get(l.getInventoryId()), priceHashMap.get(categoryDtoHashMap.get(l.getInventoryId()).getPriceId()), l)));
        } else {
            HashMap<String, CategoryDto> categoryDtoHashMap = new HashMap<>();
            categoryDtoList.forEach(cobj -> categoryDtoHashMap.put(cobj.getPriceId(), cobj));
            priceList.forEach(l -> categoryResponseList.add(CatergoryMapper.mapToCategoryResponse(categoryDtoHashMap.get(l.getPriceId()), l, inventroyHashMap.get(categoryDtoHashMap.get(l.getPriceId()).getInventoryId()))));
        }
        List<CategoryAPIResponse> filteredResponseList = categoryResponseList.stream().filter(obj -> obj.getInventory().getAvailable() >=5).collect(Collectors.toList());
        log.info("Successfully returning categoryResponseList...."+filteredResponseList);
        Header header = new Header();
        header.setMessage("API fetched the Data successfully..");
        header.setCode("200");
        header.setTraceId(tracer.currentSpan().context().traceIdString());
        catResponse.setCategoryAPIResponse(filteredResponseList);
        catResponse.setHeader(header);
        return catResponse;
    }

    @Override
    public CategoryResponse deleteCategory(CategoryDto categoryDto) {
        log.info("inside deleteCategory() method.... ");
        Query query = new Query();
        query.addCriteria(Criteria.where("CategoryType").is(categoryDto.getCategoryType()).and("categoryName").is(categoryDto.getName()));
        Category category = mongoTemplate.findOne(query, Category.class);
        if(category == null || category.getCategoryId().isEmpty()){
            throw new NullPointerException("There is no Records found to Delete....!!!!");
        }
        ResponseEntity<Void> inventoryDto = webClient.delete().uri(CategoryUtil.INVENTORY_SERVICE+"?id="+category.getInventoryId()).retrieve().toBodilessEntity().block();
        ResponseEntity<Void> priceList = webClient.delete().uri(CategoryUtil.PRICE_SERVICE+"?id="+category.getPriceId()).retrieve().toBodilessEntity().block();
        categoryRespository.deleteById(category.getCategoryId());
        log.info("Successfully Deleted.... ");
        Header header = new Header();
        header.setMessage("API Deleted the Data successfully..");
        header.setCode("204");
        header.setTraceId(tracer.currentSpan().context().traceIdString());

        catResponse.setHeader(header);
        return catResponse;
    }

    public CategoryResponse getDefaultCategory(String categoryType, int page, int size, String sortByOrder, String sortBy,Exception exception) {

        log.info("inside getDefaultCategory() method.... ");
        List<Category> list;
        if (categoryType.equals("All")) {
            list = mongoTemplate.findAll(Category.class);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("CategoryType").is(categoryType));
            list = mongoTemplate.find(query, Category.class);
        }
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : list) {
            categoryDtoList.add(CatergoryMapper.maptoCategoryDto(category, null, null));
        }
        List<CategoryAPIResponse> categoryResponseList = new ArrayList<>();
        PriceDto priceDto = new PriceDto();
        priceDto.setCurrency("NAN");
        priceDto.setAmount(0.00);
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setAvailable(0);
        inventoryDto.setAvailable(0);
        inventoryDto.setReserved(0);
        categoryDtoList.forEach(cObj -> categoryResponseList.add(CatergoryMapper.mapToCategoryResponse(cObj,priceDto,inventoryDto)));
        log.info("Successfully returning categoryResponseList from defaultFallback Method...."+categoryResponseList);
        Header header = new Header();
        header.setMessage("API not able to process the request please try after some time....");
        header.setCode("503");
        header.setTraceId(tracer.currentSpan().context().traceIdString());
        catResponse.setCategoryAPIResponse(categoryResponseList);
        catResponse.setHeader(header);
        return catResponse;
    }
    public CategoryResponse getDefaultAddMethod(CategoryDto categoryDto,Exception exception){
        Category category = CatergoryMapper.mapToCategory(categoryDto);
        CategoryDto savedDto = CatergoryMapper.maptoCategoryDto(category, new Price("NAN", 0.0),
                new Inventroy(0, 0, 0));
        PriceDto priceresponse = new PriceDto();
        priceresponse.setCurrency("NAN");
        priceresponse.setAmount(0);
        InventoryDto inventoryResponse = new InventoryDto();
        inventoryResponse.setTotal(0);
        inventoryResponse.setReserved(0);
        inventoryResponse.setAvailable(0);
        CategoryAPIResponse categoryAPIResponse = CatergoryMapper.mapToCategoryResponse(savedDto, priceresponse, inventoryResponse);
        log.info("Successfully returning categoryAPIResponse from getDefaultAddMethod ...."+categoryAPIResponse);
        Header header = new Header();
        header.setMessage("API not able to process the request please try after some time....");
        header.setCode("503");
        header.setTraceId(tracer.currentSpan().context().traceIdString());
        List<CategoryAPIResponse> categoryAPIResponseList = new ArrayList<>();
        categoryAPIResponseList.add(categoryAPIResponse);
        catResponse.setCategoryAPIResponse(categoryAPIResponseList);
        catResponse.setHeader(header);
        return catResponse;
    }
}
