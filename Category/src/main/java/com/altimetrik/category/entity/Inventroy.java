package com.altimetrik.category.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Inventroy {
    @JsonProperty("total")
    private int total;
    @JsonProperty("available")
    private int available;
    @JsonProperty("reserved")
    private int reserved;
}
