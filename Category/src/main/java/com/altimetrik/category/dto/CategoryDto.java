package com.altimetrik.category.dto;

import com.altimetrik.category.entity.Attribute;
import com.altimetrik.category.entity.Inventroy;
import com.altimetrik.category.entity.Price;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String categoryId;
    private String categoryType;
    private String name;
    private String brand;
    private String description;
    @JsonProperty("price")
    @Valid
    private Price price;
    @JsonProperty("inventory")
    private Inventroy inventory;
    private String inventoryId;
    private String priceId;
    @JsonProperty("attributes")
    @NotEmpty(message = "Please specify the Attributes")
    @Valid
    private List<Attribute> attributeDtoList;
}
