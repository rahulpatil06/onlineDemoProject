package com.altimetrik.Inventory.dto;

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
public class InventoryDto {
    private String inventoryId;
    @NotBlank(message = "Please enter Category Type Like Clothes,Electronics..")
    private String categoryType;
    @NotBlank(message = "Please enter Name...!!")
    private String name;
    @Min(value = 1, message = "Total must be greater than 0")
    private int total;
    @Min(value = 1, message = "Available must be greater than 0")
    private int available;
    private int reserved;
}
