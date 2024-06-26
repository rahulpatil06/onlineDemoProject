package com.altimetrik.category.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.annotations.NotThreadSafe;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Price {
    @JsonProperty("currency")
    @NotBlank(message = "Please enter currency type like USD,INR")
    private String currency;
    @JsonProperty("amount")
    @Min(value = 1, message = "Price must be greater than or equal to 0.00")
    @Digits(integer = 10, fraction = 2, message = "Please enter the valid Amount")
    private double amount;
}
