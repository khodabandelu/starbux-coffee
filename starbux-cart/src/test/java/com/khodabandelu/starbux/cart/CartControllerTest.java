package com.khodabandelu.starbux.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodabandelu.starbux.cart.api.commands.AddItemCommand;
import com.khodabandelu.starbux.cart.api.commands.CreateCartCommand;
import com.khodabandelu.starbux.cart.api.commands.RemoveItemCommand;
import com.khodabandelu.starbux.cart.api.dto.CreateCartResponse;
import com.khodabandelu.starbux.cart.dto.Product;
import com.khodabandelu.starbux.cart.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setup() {
        var p1 = Product.builder()
                .id("1")
                .categoryType("product")
                .name("Black Coffee")
                .price(4)
                .build();
        var p2 = Product.builder()
                .id("2")
                .categoryType("product")
                .name("Latte")
                .price(5)
                .build();
        var t1 = Product.builder()
                .id("101")
                .categoryType("topping")
                .name("Milk")
                .price(2)
                .build();

        var t2 = Product.builder()
                .id("102")
                .categoryType("topping")
                .name("Hazelnut syrup")
                .price(3)
                .build();

        given(productService.findById("1"))
                .willReturn(p1);

        given(productService.findById("2"))
                .willReturn(p2);

        given(productService.findById("101"))
                .willReturn(t1);
        given(productService.findById("102"))
                .willReturn(t2);
    }


    @Test
    public void testCreateCartCommandEndpointSuccess() throws Exception {
        var toppingIds = new ArrayList<String>();
        toppingIds.add("101");
        toppingIds.add("102");
        var command = CreateCartCommand.builder()
                .customer("Mahdi Khodabandelu")
                .productId("1")
                .toppingIds(toppingIds)
                .build();

        var jsonContent = objectMapper.writeValueAsString(command);

        this.mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testAddItemCommandEndpointSuccess() throws Exception {
        var createCartCommandToppingIds = new ArrayList<String>();
        createCartCommandToppingIds.add("101");
        var createCartCommand = CreateCartCommand.builder()
                .customer("Mahdi Khodabandelu")
                .productId("1")
                .toppingIds(createCartCommandToppingIds)
                .build();

        var createCartCommandJsonContent = objectMapper.writeValueAsString(createCartCommand);

        var createCartCommandResult = this.mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCartCommandJsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty()).andReturn();

        var createCartCommandResponse = objectMapper.readValue(createCartCommandResult.getResponse().getContentAsString(), CreateCartResponse.class);

        var addItemCommandToppingIds = new ArrayList<String>();
        addItemCommandToppingIds.add("102");
        var addItemCommand = AddItemCommand.builder()
                .productId("2")
                .toppingIds(addItemCommandToppingIds)
                .build();

        var jsonContent = objectMapper.writeValueAsString(addItemCommand);

        this.mockMvc.perform(post("/api/v1/cart/" + createCartCommandResponse.getId() + "/addItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    public void testRemoveItemCommandEndpointSuccess() throws Exception {
        var createCartCommandToppingIds = new ArrayList<String>();
        createCartCommandToppingIds.add("101");
        var createCartCommand = CreateCartCommand.builder()
                .customer("Mahdi Khodabandelu")
                .productId("1")
                .toppingIds(createCartCommandToppingIds)
                .build();

        var createCartCommandJsonContent = objectMapper.writeValueAsString(createCartCommand);

        var createCartCommandResult = this.mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCartCommandJsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty()).andReturn();

        var createCartCommandResponse = objectMapper.readValue(createCartCommandResult.getResponse().getContentAsString(), CreateCartResponse.class);

        var addItemCommandToppingIds = new ArrayList<String>();
        addItemCommandToppingIds.add("102");
        var addItemCommand = AddItemCommand.builder()
                .productId("2")
                .toppingIds(addItemCommandToppingIds)
                .build();

        var jsonContentAddItemCommand = objectMapper.writeValueAsString(addItemCommand);

        this.mockMvc.perform(post("/api/v1/cart/" + createCartCommandResponse.getId() + "/addItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContentAddItemCommand))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty());

        var removeItemCommand = RemoveItemCommand.builder()
                .productId("1")
                .build();

        var jsonContentRemoveItemCommand = objectMapper.writeValueAsString(removeItemCommand);

        this.mockMvc.perform(post("/api/v1/cart/" + createCartCommandResponse.getId() + "/removeItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContentRemoveItemCommand))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty());


    }

    @Test
    public void testConfirmCartCommandEndpointSuccess() throws Exception {
        var createCartCommandToppingIds = new ArrayList<String>();
        createCartCommandToppingIds.add("101");
        var createCartCommand = CreateCartCommand.builder()
                .customer("Mahdi Khodabandelu")
                .productId("1")
                .toppingIds(createCartCommandToppingIds)
                .build();

        var createCartCommandJsonContent = objectMapper.writeValueAsString(createCartCommand);

        var createCartCommandResult = this.mockMvc.perform(post("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCartCommandJsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty()).andReturn();

        var createCartCommandResponse = objectMapper.readValue(createCartCommandResult.getResponse().getContentAsString(), CreateCartResponse.class);

        this.mockMvc.perform(post("/api/v1/cart/" + createCartCommandResponse.getId() + "/confirmCart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    //todo test disscount
    //todo test quantity

}
