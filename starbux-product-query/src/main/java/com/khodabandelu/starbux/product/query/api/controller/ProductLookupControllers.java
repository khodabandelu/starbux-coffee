package com.khodabandelu.starbux.product.query.api.controller;

import com.khodabandelu.cqrs.core.infrastructure.QueryDispatcher;
import com.khodabandelu.starbux.common.dto.EqualityType;
import com.khodabandelu.starbux.product.query.api.dto.ProductLookupResponse;
import com.khodabandelu.starbux.product.query.api.mapper.ProductMapper;
import com.khodabandelu.starbux.product.query.api.queries.*;
import com.khodabandelu.starbux.product.query.domains.Product;
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
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductLookupControllers {
    private final Logger logger = Logger.getLogger(ProductLookupControllers.class.getName());

    private final QueryDispatcher queryDispatcher;

    private final ProductMapper productMapper;


    /**
     * {@code GET /api/v1/product} : get all products.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all products and with message successfully.
     */
    @GetMapping
    public ResponseEntity<ProductLookupResponse> getAllProducts() {
        try {
            var products = productMapper.toDto( queryDispatcher.send(new FindAllProductsQuery()));
            if (products == null || products.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = ProductLookupResponse.builder()
                    .products(products)
                    .message(MessageFormat.format("Successfully returned {0} product(s)!", products.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get all products request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new ProductLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * {@code GET /api/v1/product} : get product by id.
     * @param id id of product that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all products and with message successfully.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductLookupResponse> getAllProductById(@PathVariable("id") String id) {
        try {
            var products = productMapper.toDto(queryDispatcher.send(new FindProductByIdQuery(id)));
            if (products == null || products.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = ProductLookupResponse.builder()
                    .products(products)
                    .message("Successfully returned product!")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get products by ID request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new ProductLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code GET /api/v1/product/byName} : get product by name.
     * @param name name of product that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all products and with message successfully.
     */
    @GetMapping("/byName/{name}")
    public ResponseEntity<ProductLookupResponse> getAllProductByName(@PathVariable("name") String name) {
        try {
            var products = productMapper.toDto(queryDispatcher.send(new FindProductByNameQuery(name)));
            if (products == null || products.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = ProductLookupResponse.builder()
                    .products(products)
                    .message("Successfully returned product!")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get products by name request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new ProductLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code GET /api/v1/product/byName} : get all products by category type.
     * @param categoryType category type of product that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all products and with message successfully.
     */
    @GetMapping("/byCategory/{categoryType}")
    public ResponseEntity<ProductLookupResponse> getAllProductByCategoryType(@PathVariable("categoryType") String categoryType) {
        try {
            var products = productMapper.toDto(queryDispatcher.send(new FindProductByCategoryQuery(categoryType)));
            if (products == null || products.size() == 0) {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = ProductLookupResponse.builder()
                    .products(products)
                    .message("Successfully returned product!")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var safeErrorMessage = "Failed to complete get products by category request!";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new ProductLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * {@code GET /api/v1/product/byPrice} : get all products by range of price.
     * @param equalityType equality type of price that you want to filter price.
     * @param price price of product that you want to fetch it.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all products and with message successfully.
     */
    @GetMapping("/byPrice/{equalityType}/{price}")
    public ResponseEntity<ProductLookupResponse> getAllProductByPrice(@PathVariable("equalityType") EqualityType equalityType, @PathVariable("price") double price) {
        try {
            var products = productMapper.toDto(queryDispatcher.send(new FindProductByPriceQuery(equalityType, price)));
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
