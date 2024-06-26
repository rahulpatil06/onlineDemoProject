

import Inventory.InventoryApplication;
import Inventory.dto.InventoryDto;
import Inventory.services.InvertoryTestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(classes= InventoryApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class InventoryControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    InvertoryTestService inventoryService;

    @Test
    public void getDetailedInvetoryTest() throws Exception{
        String END_POINT = "http://localhost:8118/inventoryTest/inventoryDetails?type=Electronics";

        InventoryDto inventoryDto1 = new InventoryDto("64621b8b9e467d01adb9ac5b", "Electronics","Laptop",25,10,15);
        InventoryDto inventoryDto2 = new InventoryDto("64652eee5499243b2a0239af", "Electronics","Smart Speaker",30,25,5);
        InventoryDto inventoryDto3 = new InventoryDto("64647f6046b08e5a6fff425c", "Electronics","Wireless Headphones",40,30,10);
        List<InventoryDto> inventoryDtoList = Arrays.asList(inventoryDto1,inventoryDto2,inventoryDto3);

        ResponseEntity<List> response = restTemplate.getForEntity(
                new URL("http://localhost:" + 8118 + "//inventoryTest/inventoryDetails?type=Electronics").toString(), List.class);
       assertEquals(objectMapper.writeValueAsString(inventoryDtoList), objectMapper.writeValueAsString(response.getBody()));
//         Mockito.when(inventoryService.getInventoryDetails("Clothes", 1, 10, "asc", "inventory")).thenReturn(new ArrayList<InventoryDto>());
//         ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(END_POINT))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andDo(print());
//         System.out.println(resultActions);

        List<InventoryDto> inventoryDtos = inventoryService.getInventoryDetails("Clothes", 1, 10, "asc", "inventory");
        Assertions.assertThat(inventoryDtos).extracting(InventoryDto::getInventoryId).toString().contains("64621b8b9e467d01adb9ac5b");
    }
    @Test
    public void testErrorHandlingReturnsBadRequest() {
        String url = "http://localhost:8118/wrong";

        try {
            restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }
}
