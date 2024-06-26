package com.altimetrik.Inventory;

import brave.Tracer;
import com.altimetrik.Inventory.dto.InventoryDto;

import com.altimetrik.Inventory.services.InvertoryTestService;
import com.altimetrik.Inventory.testErrorHandling.TestCustomErrorHandling;
import com.altimetrik.Inventory.testService.TestInventoryService;
import com.altimetrik.Inventory.testcontroller.TestInventoryController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(classes= InventoryApplication.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class InventoryApplicationTests {

}
