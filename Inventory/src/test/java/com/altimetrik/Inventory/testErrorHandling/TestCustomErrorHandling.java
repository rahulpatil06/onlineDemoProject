package com.altimetrik.Inventory.testErrorHandling;

import brave.Tracer;
import com.altimetrik.Inventory.InventoryApplication;
import com.altimetrik.Inventory.services.InvertoryTestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes= InventoryApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TestCustomErrorHandling {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    InvertoryTestService inventoryService;
    @MockBean
    Tracer tracer;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void givenInvalidInventory_thenReturnEmpty() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8082/inventory-service/inventories?type=Kids Wear"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.message.errorMessage", CoreMatchers.containsString("No Such Category Exists...!!!!")));

    }
}
