package com.mediasoft.services.impls;

import com.mediasoft.controllers.dtos.ProductDto;
import com.mediasoft.exception.ProductNotFoundException;
import com.mediasoft.models.Product;
import com.mediasoft.repositories.ProductRepository;
import com.mediasoft.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(ProductDto dto) {
        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .article(dto.getArticle())
                .quantity(dto.getQuantity())
                .creationDate(LocalDate.now())
                .quantityLastUpdateDate(LocalDateTime.now())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product update(ProductDto dto, UUID productId) {
        Product product = getById(productId);

        if (Objects.nonNull(dto.getName())) {
            product.setName(dto.getName());
        }
        if (Objects.nonNull(dto.getDescription())) {
            product.setDescription(dto.getDescription());
        }
        if (Objects.nonNull(dto.getCategory())) {
            product.setCategory(dto.getCategory());
        }
        if (Objects.nonNull(dto.getPrice())) {
            product.setPrice(dto.getPrice());
        }
        if (Objects.nonNull(dto.getQuantity())) {
            product.setQuantityLastUpdateDate(LocalDateTime.now());
        }
        return productRepository.save(product);
    }

    @Override
    public Product getById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(
                String.format("Product with id: [%s] not found!", productId)
        ));
    }

    @Override
    public Product deleteById(UUID productID) {
        Product product = getById(productID);
        productRepository.delete(product);
        return product;
    }
}


