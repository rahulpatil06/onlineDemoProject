package com.altimetrik.Inventory.dto;

import com.altimetrik.Inventory.errorHandling.CustomErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class InventoryResponse {
    private Header header;
    private  InventoryDto inventoryResponse;
    private CustomErrorResponse errorDetails;
}
