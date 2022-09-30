package com.khodabandelu.starbux.cart.api.controller;

import com.khodabandelu.cqrs.core.infrastructure.QueryDispatcher;
import com.khodabandelu.starbux.cart.api.dto.CartAggregateResponse;
import com.khodabandelu.starbux.cart.api.dto.CartLookupResponse;
import com.khodabandelu.starbux.cart.api.dto.ProductLookupResponse;
import com.khodabandelu.starbux.cart.api.queries.*;
import com.khodabandelu.starbux.cart.dto.Product;
import com.khodabandelu.starbux.cart.entities.CartEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartLookupController {
    private final Logger logger = Logger.getLogger(CartLookupController.class.getName());
    private final QueryDispatcher queryDispatcher;

    @GetMapping
    public ResponseEntity<CartLookupResponse> getAllCarts() {
        try {
            List<CartEntity> carts = queryDispatcher.send(new FindAllCartsQuery());
            if (carts == null || carts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = CartLookupResponse.builder()
                    .carts(carts)
                    .message(MessageFormat.format("Successfully returned {0} cart(s)!", carts.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all cart request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new CartLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<CartLookupResponse> getAllCartById(@PathVariable("id") String id) {
        try {
            List<CartEntity> carts = queryDispatcher.send(new FindCartByIdQuery(id));
            if (carts == null || carts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = CartLookupResponse.builder()
                    .carts(carts)
                    .message("Successfully returned cart!")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get carts by ID request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new CartLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byCustomer/{customer}")
    public ResponseEntity<CartLookupResponse> getAllCartByCustomer(@PathVariable("customer") String customer) {
        try {
            List<CartEntity> carts = queryDispatcher.send(new FindCartByCustomerQuery(customer));
            if (carts == null || carts.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = CartLookupResponse.builder()
                    .carts(carts)
                    .message("Successfully returned cart!")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get carts by customer request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new CartLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sumTotalAmount/{customer}")
    public ResponseEntity<CartAggregateResponse> getSumTotalAmountAllCartsByCustomer(@PathVariable("customer") String customer) {
        try {
            Number sumTotalAmount = queryDispatcher.send(new FindTotalPriceAllCartsByCustomerQuery(customer));
            if (sumTotalAmount == null) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = CartAggregateResponse.builder()
                    .aggregateValue(sumTotalAmount)
                    .message("Successfully returned product!")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get aggregate amount by customer request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new CartAggregateResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //todo create report module to expose report endpoints
    @GetMapping("/mostUsedTopping")
    public ResponseEntity<ProductLookupResponse> mostUsedTopping() {
        try {
            List<Product> products = queryDispatcher.send(new FindMostUsedToppingQuery());
            if (products == null || products.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = ProductLookupResponse.builder()
                    .products(products)
                    .message(MessageFormat.format("Successfully returned {0}  product(s)!", products.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get products by price request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new ProductLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
