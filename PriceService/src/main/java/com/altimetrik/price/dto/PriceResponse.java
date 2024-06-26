package com.altimetrik.price.dto;

import com.altimetrik.price.errorHandling.CustomErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class PriceResponse {
    private Header header;
    private  PriceDto priceResponseDto;
    private CustomErrorResponse errorDetails;
}
