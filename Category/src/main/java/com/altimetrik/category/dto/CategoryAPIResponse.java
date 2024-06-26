package com.altimetrik.category.dto;

import com.altimetrik.category.entity.Attribute;
import com.altimetrik.category.entity.Inventroy;
import com.altimetrik.category.entity.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.jar.Attributes;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAPIResponse {
    private String categoryType;
    private String name;
    private String brand;
    private String description;
    private Price price;
    private Inventroy inventory;
    private List<Attribute> attributes;
}
