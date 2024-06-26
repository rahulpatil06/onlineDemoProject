package com.altimetrik.price.entity;

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
@Document(collection="PriceDB")
public class Price {
    @Id
    private String priceId;
    private String categoryType;
    private String name;
    private String currency;
    private double amount;
}
