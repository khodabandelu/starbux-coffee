package com.khodabandelu.starbux.product.query;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodabandelu.starbux.common.events.ProductCreatedEvent;
import com.khodabandelu.starbux.product.query.infrastructure.handlers.EventHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventHandler eventHandler;

    @Test
    public void testGetAllProductsEndpointSuccess() throws Exception {

        var productCreatedEvent1 = ProductCreatedEvent.builder()
                .id("1")
                .categoryType("product")
                .name("Black Coffee")
                .price(4)
                .build();
        var productCreatedEvent2 = ProductCreatedEvent.builder()
                .id("2")
                .categoryType("product")
                .name("Latte")
                .price(5)
                .build();

        eventHandler.on(productCreatedEvent1);
        eventHandler.on(productCreatedEvent2);
        // Get all the users
        this.mockMvc.perform(get("/api/v1/product")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.products.[*].name").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.products.[*].categoryType").value(hasItem("product")))
                .andExpect(jsonPath("$.products.[*].price").value(hasItem(4.0)));
    }

    @Test
    public void testGetAllProductByIdEndpointSuccess() throws Exception {

        var productCreatedEvent1 = ProductCreatedEvent.builder()
                .id("1")
                .categoryType("product")
                .name("Black Coffee")
                .price(4)
                .build();

        eventHandler.on(productCreatedEvent1);
        // Get all the users
        this.mockMvc.perform(get("/api/v1/product/byId/"+"1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.products.[*].name").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.products.[*].categoryType").value(hasItem("product")))
                .andExpect(jsonPath("$.products.[*].price").value(hasItem(4.0)));
    }

    @Test
    public void testGetAllProductByNameEndpointSuccess() throws Exception {

        var productCreatedEvent1 = ProductCreatedEvent.builder()
                .id("1")
                .categoryType("product")
                .name("Black Coffee")
                .price(4)
                .build();

        eventHandler.on(productCreatedEvent1);
        // Get all the users
        this.mockMvc.perform(get("/api/v1/product/byName/"+"Black Coffee")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.products.[*].name").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.products.[*].categoryType").value(hasItem("product")))
                .andExpect(jsonPath("$.products.[*].price").value(hasItem(4.0)));
    }

    @Test
    public void testGetAllProductByCategoryEndpointSuccess() throws Exception {

        var productCreatedEvent1 = ProductCreatedEvent.builder()
                .id("1")
                .categoryType("product")
                .name("Black Coffee")
                .price(4)
                .build();

        eventHandler.on(productCreatedEvent1);
        // Get all the users
        this.mockMvc.perform(get("/api/v1/product/byCategory/"+"product")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.products.[*].name").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.products.[*].categoryType").value(hasItem("product")))
                .andExpect(jsonPath("$.products.[*].price").value(hasItem(4.0)));
    }


}
