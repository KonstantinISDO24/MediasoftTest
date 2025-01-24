package com.mediasoft.services;

import com.mediasoft.controllers.dtos.ProductDto;
import com.mediasoft.exception.ProductNotFoundException;
import com.mediasoft.models.Product;
import com.mediasoft.repositories.ProductRepository;
import com.mediasoft.services.impls.ProductServiceImpl;
import com.mediasoft.utils.DtoUtils;
import com.mediasoft.utils.ModelUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Test get all products")
    void getAllProduct_Successful() {
        when(productRepository.findAll()).thenReturn(List.of(ModelUtils.createProduct(UUID.randomUUID())));

        List<Product> products = productService.getAll();

        assertEquals(1, products.size());
    }

    @Test
    @DisplayName("Test create product")
    void createProduct_Successful() {
        Product product = ModelUtils.createProduct(UUID.randomUUID());
        ProductDto productDto = DtoUtils.createProductDto();

        when(productRepository.save(any())).thenReturn(product);

        Product result = productService.create(productDto);

        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getCategory(), result.getCategory());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getQuantity(), result.getQuantity());
        assertEquals(product.getArticle(), result.getArticle());
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getQuantityLastUpdateDate());
    }

    @Test
    @DisplayName("Test update product")
    void updateProduct_Successful() {
        Product product = ModelUtils.createProduct(UUID.randomUUID());
        ProductDto productDto = DtoUtils.createProductDto();

        when(productRepository.save(any())).thenReturn(product);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        Product result = productService.update(productDto, product.getId());

        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getCategory(), result.getCategory());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getQuantity(), result.getQuantity());
        assertEquals(product.getArticle(), result.getArticle());
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getQuantityLastUpdateDate());
    }

    @Test
    @DisplayName("Test update not existing product")
    void updateProduct_ThrowsException() {
        UUID id = UUID.randomUUID();
        ProductDto productDto = DtoUtils.createProductDto();

        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.update(productDto, id));
    }

    @Test
    @DisplayName("Test delete product")
    void deleteProduct_Successful() {
        Product product = ModelUtils.createProduct(UUID.randomUUID());

        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any());

        Product result = productService.deleteById(product.getId());

        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getCategory(), result.getCategory());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getQuantity(), result.getQuantity());
        assertEquals(product.getArticle(), result.getArticle());
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getQuantityLastUpdateDate());
    }

    @Test
    @DisplayName("Test delete not existing product")
    void deleteProduct_ThrowsException() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Test get product by id")
    void getByIdProduct_Successful() {
        Product product = ModelUtils.createProduct(UUID.randomUUID());

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        Product result = productService.getById(product.getId());

        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getCategory(), result.getCategory());
        assertEquals(product.getPrice(), result.getPrice());
        assertEquals(product.getQuantity(), result.getQuantity());
        assertEquals(product.getArticle(), result.getArticle());
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getQuantityLastUpdateDate());
    }

    @Test
    @DisplayName("Test get not existing product by id")
    void getByIdProduct_ThrowsException() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getById(UUID.randomUUID()));
    }
}
