package com.altimetrik.Inventory.testService;

import brave.Tracer;
import com.altimetrik.Inventory.InventoryApplication;
import com.altimetrik.Inventory.dto.InventoryDto;
import com.altimetrik.Inventory.services.InvertoryTestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest(classes= InventoryApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TestInventoryService {
    @MockBean
    InvertoryTestService inventoryService;
    @MockBean
    Tracer tracer;

    @Test
    public void getDetailedInvetoryTest() throws Exception {

        InventoryDto inventoryDto1 = new InventoryDto("64621b8b9e467d01adb9ac5b", "Electronics", "Laptop", 25, 10, 15);
        InventoryDto inventoryDto2 = new InventoryDto("64652eee5499243b2a0239af", "Electronics", "Smart Speaker", 30, 25, 5);
        InventoryDto inventoryDto3 = new InventoryDto("64647f6046b08e5a6fff425c", "Electronics", "Wireless Headphones", 40, 30, 10);
        List<InventoryDto> inventoryDtoList = Arrays.asList(inventoryDto1, inventoryDto2, inventoryDto3);

        when(inventoryService.getInventoryDetails("Electronics", 1, 10, "asc", "inventory")).thenReturn(inventoryDtoList);
        List<InventoryDto> inventoryList = inventoryService.getInventoryDetails("Electronics", 1, 10, "asc", "inventory");
        assertEquals(inventoryDtoList.size(), inventoryList.size());
        verify(inventoryService, times(1)).getInventoryDetails("Electronics", 1, 10, "asc", "inventory");
    }
}
