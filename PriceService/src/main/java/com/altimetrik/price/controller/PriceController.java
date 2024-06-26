package com.altimetrik.price.controller;

import brave.Tracer;
import com.altimetrik.price.dto.PriceDto;
import com.altimetrik.price.dto.PriceResponse;
import com.altimetrik.price.errorHandling.ErrorHandlingControllerAdvice;
import com.altimetrik.price.service.PriceService;
import jakarta.validation.Valid;
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/price-service")
@Slf4j
public class PriceController {

    @Autowired
    Tracer tracer;
    @Autowired
    PriceService priceService;

    @PostMapping("prices")
    public ResponseEntity<PriceDto> addPriceCategory(@RequestBody @Valid PriceDto priceDto) {
        log.info("Inside addPriceCategory() method in Controller Layer ....");
        PriceDto priceRespose = priceService.addPrices(priceDto);
        log.info("Successfully returning from Controller Layer .... "+priceRespose);
        return new ResponseEntity<>(priceRespose, HttpStatus.CREATED);
    }

    @GetMapping("prices")
    public ResponseEntity<List<PriceDto>> getPriceDetailedList(@RequestParam(value = "type",defaultValue = "All") String CategoryType,
                   @RequestParam(value = "pageNo",defaultValue = "1")int page, @RequestParam(value = "pageSize",defaultValue = "10") int size,
                     @RequestParam(value="order",defaultValue = "asc")String sortByOrder,@RequestParam(value = "sortBy",defaultValue = "price")String sortBy) {
        log.info("Inside getPriceDetailedList() method in Controller Layer ....");
        List<PriceDto> priceDtoList;
        priceDtoList = priceService.getPriceList(CategoryType,page,size,sortByOrder,sortBy);
        log.info("Successfully returning from Controller Layer .... "+priceDtoList);
        return new ResponseEntity<List<PriceDto>>(priceDtoList, HttpStatus.OK);
    }
    @PutMapping("prices")
    public ResponseEntity<PriceResponse> updatePrice(@RequestBody @Valid PriceDto priceDto,@RequestHeader String role) throws Exception, ServiceUnavailableException {
        if(!role.equals("Admin")){
            throw new AccessDeniedException("Not Authorised...!!!");
        }
        log.info("Inside updatePrice() method in Controller ....");
        PriceResponse priceResponse = priceService.updatePrice(priceDto);
        log.info("Successfully returning priceDtoResponse...."+priceResponse);
        return ResponseEntity.status(HttpStatus.OK).build().ok().body(priceResponse);
    }
    @DeleteMapping("prices")
    public ResponseEntity deletePrice(@RequestParam String id) throws ErrorHandlingControllerAdvice {
        log.info("Inside deletePrice() method in Controller ....");
        priceService.deletePrice(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build().ok().body("Deleted Successfully...");
    }
}
