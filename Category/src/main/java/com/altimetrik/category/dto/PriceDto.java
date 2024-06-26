package com.altimetrik.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PriceDto {
    private String priceId;
    private String categoryType;
    private String name;
    private String currency;
    private double amount;
}
