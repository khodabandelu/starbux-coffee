package com.khodabandelu.starbux.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodabandelu.starbux.cart.api.commands.AddItemCommand;
import com.khodabandelu.starbux.cart.api.commands.CreateCartCommand;
import com.khodabandelu.starbux.cart.api.commands.RemoveItemCommand;
import com.khodabandelu.starbux.cart.api.dto.CreateCartResponse;
import com.khodabandelu.starbux.cart.dao.CartRepository;
import com.khodabandelu.starbux.cart.dto.Product;
import com.khodabandelu.starbux.cart.entities.CartEntity;
import com.khodabandelu.starbux.cart.entities.CartItemEntity;
import com.khodabandelu.starbux.cart.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CartLookupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartRepository cartRepository;

    @BeforeEach
    public void setup() {
        var i1 = CartItemEntity.builder()
                .productId("1")
                .masterProductId(null)
                .price(4)
                .qty(1)
                .productName("Black Coffee")
                .build();
        var i2 = CartItemEntity.builder()
                .productId("101")
                .masterProductId("1")
                .price(2)
                .qty(1)
                .productName("Milk")
                .build();

        var items1 = new ArrayList<CartItemEntity>();
        items1.add(i1);
        items1.add(i2);
        var c1 = CartEntity.builder()
                .id("1")
                .customer("Mahdi")
                .createdDate(new Date())
                .confirmed(false)
                .originalPrice(6)
                .discountPrice(0)
                .totalPrice(6)
                .items(items1)
                .build();


        var i3 = CartItemEntity.builder()
                .productId("2")
                .masterProductId(null)
                .price(5)
                .qty(1)
                .productName("Latte")
                .build();
        var i4 = CartItemEntity.builder()
                .productId("101")
                .masterProductId("2")
                .price(2)
                .qty(1)
                .productName("Milk")
                .build();
        var items2 = new ArrayList<CartItemEntity>();
        items2.add(i3);
        items2.add(i4);
        var c2 = CartEntity.builder()
                .id("1")
                .customer("Mahdi")
                .createdDate(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .confirmed(true)
                .originalPrice(7)
                .discountPrice(0)
                .totalPrice(7)
                .items(items2)
                .build();

        var carts = new ArrayList<CartEntity>();
        carts.add(c1);
        carts.add(c2);

        var currentCarts = new ArrayList<CartEntity>();
        carts.add(c1);
        given(cartRepository.findAll()).willReturn(carts);

        given(cartRepository.findByCustomer("Mahdi")).willReturn(carts);
        given(cartRepository.findByCustomerAndConfirmedFalse("Mahdi")).willReturn(Optional.of(c1));
        given(cartRepository.findFirstByCustomerOrderByCreatedDateDesc("Mahdi")).willReturn(Optional.of(c1));
        given(cartRepository.findFirstByCustomerAndConfirmedOrderByCreatedDateDesc("Mahdi",true)).willReturn(Optional.of(c2));

        given(cartRepository.findById("1")).willReturn(Optional.of(c1));

        given(cartRepository.findById("2")).willReturn(Optional.of(c2));

    }


    @Test
    public void testGetAllCartEndpointSuccess() throws Exception {
        this.mockMvc.perform(get("/api/v1/cart")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Latte")))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(5.0)))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(5.0)))
                .andExpect(jsonPath("$.carts.[*].originalPrice").value(hasItem(7.0)))
                .andExpect(jsonPath("$.carts.[*].totalPrice").value(hasItem(7.0)));
    }

    @Test
    public void testGetCartByIdEndpointSuccess() throws Exception {
        this.mockMvc.perform(get("/api/v1/cart/"+"1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Milk")))
                .andExpect(jsonPath("$.carts.[*].items.[*].productId").value(hasItem("101")))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(4.0)))
                .andExpect(jsonPath("$.carts.[*].originalPrice").value(hasItem(6.0)))
                .andExpect(jsonPath("$.carts.[*].discountPrice").value(hasItem(0.0)))
                .andExpect(jsonPath("$.carts.[*].totalPrice").value(hasItem(6.0)));
    }

    @Test
    public void testGetAllCartByCustomerEndpointSuccess() throws Exception {
        this.mockMvc.perform(get("/api/v1/cart/all/customer/"+"Mahdi")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Latte")))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(5.0)))
                .andExpect(jsonPath("$.carts.[*].originalPrice").value(hasItem(7.0)))
                .andExpect(jsonPath("$.carts.[*].totalPrice").value(hasItem(7.0)));
    }

    @Test
    public void testGetCurrentCartByCustomerEndpointSuccess() throws Exception {
        this.mockMvc.perform(get("/api/v1/cart/current/customer/"+"Mahdi")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Milk")))
                .andExpect(jsonPath("$.carts.[*].confirmed").value(hasItem(false)))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(4.0)))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(2.0)))
                .andExpect(jsonPath("$.carts.[*].originalPrice").value(hasItem(6.0)))
                .andExpect(jsonPath("$.carts.[*].discountPrice").value(hasItem(0.0)))
                .andExpect(jsonPath("$.carts.[*].totalPrice").value(hasItem(6.0)));
    }

    @Test
    public void testGetLastCartByCustomerEndpointSuccess() throws Exception {
        this.mockMvc.perform(get("/api/v1/cart/last/customer/"+"Mahdi")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Black Coffee")))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Milk")))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(2.0)))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(4.0)))
                .andExpect(jsonPath("$.carts.[*].originalPrice").value(hasItem(6.0)))
                .andExpect(jsonPath("$.carts.[*].discountPrice").value(hasItem(0.0)))
                .andExpect(jsonPath("$.carts.[*].totalPrice").value(hasItem(6.0)));
    }

    @Test
    public void testGetLastConfirmedCartByCustomerEndpointSuccess() throws Exception {
        this.mockMvc.perform(get("/api/v1/cart/lastConfirmed/customer/"+"Mahdi")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Latte")))
                .andExpect(jsonPath("$.carts.[*].items.[*].productName").value(hasItem("Milk")))
                .andExpect(jsonPath("$.carts.[*].confirmed").value(hasItem(true)))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(2.0)))
                .andExpect(jsonPath("$.carts.[*].items.[*].price").value(hasItem(5.0)))
                .andExpect(jsonPath("$.carts.[*].originalPrice").value(hasItem(7.0)))
                .andExpect(jsonPath("$.carts.[*].discountPrice").value(hasItem(0.0)))
                .andExpect(jsonPath("$.carts.[*].totalPrice").value(hasItem(7.0)));
    }


   //todo test mostusedTopping
    //todo test sumtotalamount
}
