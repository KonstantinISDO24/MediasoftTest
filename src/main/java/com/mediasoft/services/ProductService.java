package com.mediasoft.services;

import com.mediasoft.controllers.dtos.ProductDto;
import com.mediasoft.models.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getAll();

    Product create(ProductDto dto);

    Product update(ProductDto dto, UUID productId);

    Product getById(UUID productId);

    Product deleteById(UUID productID);
}
