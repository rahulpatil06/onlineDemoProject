package com.altimetrik.category.dto;

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
    private String categoryType;
    private String name;
    private int total;
    private int available;
    private int reserved;
}
