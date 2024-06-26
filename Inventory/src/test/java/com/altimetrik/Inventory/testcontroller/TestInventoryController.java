package com.altimetrik.Inventory.testcontroller;

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
public class TestInventoryController {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    Tracer tracer;

    @Test
    public void inventoryControllerTest() throws Exception{
        String END_POINT = "http://localhost:8082/inventory-service/inventories?type=Clothes";
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(END_POINT));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(5)));
    }
}
