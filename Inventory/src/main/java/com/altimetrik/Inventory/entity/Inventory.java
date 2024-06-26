package com.altimetrik.Inventory.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "Inventory")
public class Inventory {

    @Id
    private String inventoryId;
    private String categoryType;
    private String name;
    private int total;
    private int available;
    private int reserved;
}
