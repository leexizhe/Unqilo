package com.uniqlo.productservice;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniqlo.productservice.dto.ProductRequest;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // TODO: 21/7/2023 should change to h2 database and test
    @Test
    void testCreateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(APPLICATION_JSON)
                        .content(getProductRequest()))
                .andExpect(status().isCreated());
    }

    private String getProductRequest() throws JsonProcessingException {
        ProductRequest productRequest = ProductRequest.builder()
                .name("Samsung S23")
                .description("Samsung")
                .price(BigDecimal.valueOf(1000))
                .build();
        return objectMapper.writeValueAsString(productRequest);
    }
}
