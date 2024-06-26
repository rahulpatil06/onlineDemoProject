package com.altimetrik.category.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Attribute {
    @JsonProperty("name")
    @NotBlank(message = "Please specify the Attribute Name")
    private String name;
    @JsonProperty("value")
    @NotBlank(message = "Please specify the Attribute Value")
    private String value;
}
