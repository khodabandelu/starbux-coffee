package com.khodabandelu.starbux.cart.services.impl;

import com.khodabandelu.starbux.cart.dto.Product;
import com.khodabandelu.starbux.cart.dto.ProductResponse;
import com.khodabandelu.starbux.cart.services.ProductService;
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
        var url = "http://" + productServiceHostname + ":" + productServicePort + "/api/v1/product/byId/" + id;
        var response = restTemplate.getForObject(url, ProductResponse.class);
        return response != null && response.getProducts() != null ? response.getProducts().get(0) : null;
    }

}
