package com.uniqlo.productservice.service;

import com.uniqlo.productservice.dto.ProductRequest;
import com.uniqlo.productservice.dto.ProductResponse;
import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProduct();
}
