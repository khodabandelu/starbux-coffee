package com.example.starbux.cart.services.impl;

import com.example.starbux.cart.dto.Product;
import com.example.starbux.cart.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Value("${services.product.hostname}")
    private String productServiceHostname;
    @Value("${services.product.port}")
    private String productServicePort;

    private final RestTemplate restTemplate;

    //todo add cache product
    @Override
    public Product findById(String id) {
        var url = productServiceHostname + ":" + productServicePort + "/api/v1/product/" + id;
        return restTemplate.getForObject(url, Product.class);
    }


}
