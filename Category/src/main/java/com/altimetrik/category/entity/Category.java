package com.altimetrik.category.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "Category")
public class Category {
    @Id
    private String categoryId;
    private String categoryType;
    private String categoryName;
    private String brand;
    private String description;
    private String inventoryId;
    private String priceId;
    @JsonProperty("attributes")
    private List<Attribute> attributes;
}
