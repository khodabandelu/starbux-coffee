package com.khodabandelu.starbux.product.cmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodabandelu.starbux.product.cmd.api.commands.CreateProductCommand;
import com.khodabandelu.starbux.product.cmd.api.commands.UpdateProductInfoCommand;
import com.khodabandelu.starbux.product.cmd.domain.CategoryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProductEndpointNotAuthorized() throws Exception {
        var command = CreateProductCommand.builder()
                .categoryType(CategoryType.product)
                .name("Black Coffee")
                .price(4)
                .build();

        var jsonContent = objectMapper.writeValueAsString(command);

        this.mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testCreateProductEndpointSuccess() throws Exception {
        var command = CreateProductCommand.builder()
                .categoryType(CategoryType.product)
                .name("Black Coffee")
                .price(4)
                .build();

        var jsonContent = objectMapper.writeValueAsString(command);

        this.mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUpdateProductEndpointSuccess() throws Exception {
        var createProductCommand = CreateProductCommand.builder()
                .categoryType(CategoryType.product)
                .name("Black Coffee")
                .price(4)
                .build();


        var jsonContentCreateProductCommand = objectMapper.writeValueAsString(createProductCommand);

        var createProductCommandResult = this.mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContentCreateProductCommand))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andReturn();

        var createProductCommandResponse = objectMapper.readValue(createProductCommandResult.getResponse().getContentAsString(), CreateProductCommand.class);

        var updateProductInfoCommand = UpdateProductInfoCommand.builder()
                .categoryType(CategoryType.topping)
                .name("Milk")
                .price(4)
                .build();


        var jsonContentUpdateProductInfoCommand = objectMapper.writeValueAsString(updateProductInfoCommand);

        this.mockMvc.perform(put("/api/v1/product/" + createProductCommandResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContentUpdateProductInfoCommand))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteProductEndpointSuccess() throws Exception {
        var createProductCommand = CreateProductCommand.builder()
                .categoryType(CategoryType.product)
                .name("Black Coffee")
                .price(4)
                .build();


        var jsonContentCreateProductCommand = objectMapper.writeValueAsString(createProductCommand);

        var createProductCommandResult = this.mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContentCreateProductCommand))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andReturn();

        var createProductCommandResponse = objectMapper.readValue(createProductCommandResult.getResponse().getContentAsString(), CreateProductCommand.class);


        this.mockMvc.perform(delete("/api/v1/product/" + createProductCommandResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUpdateProductEndpointIncorrectProductId() throws Exception {
        var updateProductInfoCommand = UpdateProductInfoCommand.builder()
                .categoryType(CategoryType.topping)
                .name("Milk")
                .price(4)
                .build();


        var jsonContentUpdateProductInfoCommand = objectMapper.writeValueAsString(updateProductInfoCommand);
        this.mockMvc.perform(put("/api/v1/product/" + "incorrect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContentUpdateProductInfoCommand))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteProductEndpointIncorrectProductId() throws Exception {
        this.mockMvc.perform(delete("/api/v1/product/" + "incorrect")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }


}
