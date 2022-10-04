package com.khodabandelu.starbux.cart.api.controller;

import com.khodabandelu.cqrs.core.infrastructure.QueryDispatcher;
import com.khodabandelu.starbux.cart.api.dto.CartAggregateResponse;
import com.khodabandelu.starbux.cart.api.dto.CartLookupResponse;
import com.khodabandelu.starbux.cart.api.dto.MostUsedProductDto;
import com.khodabandelu.starbux.cart.api.dto.MostUsedProductResponse;
import com.khodabandelu.starbux.cart.api.queries.*;
import com.khodabandelu.starbux.cart.entities.CartEntity;
import com.khodabandelu.starbux.common.dto.BaseResponse;
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

    /**
     * {@code GET /api/v1/cart} : get all carts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all carts and with message successfully.
     */
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

    /**
     * {@code GET /api/v1/cart} : get cart by id.
     *
     * @param id id of cart that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all products and with message successfully.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CartLookupResponse> getCartById(@PathVariable("id") String id) {
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

    /**
     * {@code GET /api/v1/cart/customer} : get all carts by customer name.
     *
     * @param customer customer name of cart that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all carts and with message successfully.
     */
    @GetMapping("/all/customer/{customer}")
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

    /**
     * {@code GET /api/v1/cart/current/customer} : get current cart by customer name.
     *
     * @param customer customer name of cart that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body of current cart and with message successfully.
     */
    @GetMapping("/current/customer/{customer}")
    public ResponseEntity<CartLookupResponse> getCurrentCartByCustomer(@PathVariable("customer") String customer) {
        try {
            List<CartEntity> carts = queryDispatcher.send(new FindCurrentCartByCustomerQuery(customer));
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

    /**
     * {@code GET /api/v1/cart/last/customer} : get last cart by customer name.
     *
     * @param customer customer name of cart that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body of last cart and with message successfully.
     */
    @GetMapping("/last/customer/{customer}")
    public ResponseEntity<CartLookupResponse> getLastCartByCustomer(@PathVariable("customer") String customer) {
        try {
            List<CartEntity> carts = queryDispatcher.send(new FindLastCartByCustomerQuery(customer));
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

    /**
     * {@code GET /api/v1/cart/current/customer} : get last confirmed cart by customer name.
     *
     * @param customer customer name of cart that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all products and with message successfully.
     */
    @GetMapping("/lastConfirmed/customer/{customer}")
    public ResponseEntity<CartLookupResponse> getLastConfirmedCartByCustomer(@PathVariable("customer") String customer) {
        try {
            List<CartEntity> carts = queryDispatcher.send(new FindLastConfimredCartByCustomerQuery(customer));
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

    /**
     * {@code GET /api/v1/cart/customer/customer/sumTotalAmount} : get sum total amount of carts by customer name.
     *
     * @param customer customer name of cart that you want to fetch this aggregate.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body of aggregate value and with message successfully.
     */
    @GetMapping("/sumTotalAmount/customer/{customer}")
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

    /**
     * {@code GET /api/v1/cart/report/mostUsedTopping} : get report od  most used toppings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and  with body all products and with message successfully.
     */
    //todo create report module to expose report's endpoints
    @GetMapping("/mostUsedTopping")
    public ResponseEntity<BaseResponse> mostUsedTopping() {
        try {
            List<MostUsedProductDto> products = queryDispatcher.send(new FindMostUsedToppingQuery());
            if (products == null || products.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = MostUsedProductResponse.builder()
                    .products(products)
                    .message(MessageFormat.format("Successfully returned {0}  product(s)!", products.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get products by most used request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
