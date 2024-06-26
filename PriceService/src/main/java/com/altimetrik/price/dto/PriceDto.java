package com.altimetrik.price.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Please enter currency type like USD,INR")
    private String currency;
    @Min(value = 1, message = "Price must be greater than or equal to 0.00")
    @Digits(integer = 10, fraction = 2, message = "Please enter the valid Amount")
    private double amount;
}
