package com.mediasoft.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediasoft.controllers.dtos.ProductDto;
import com.mediasoft.exception.ProductNotFoundException;
import com.mediasoft.models.Product;
import com.mediasoft.services.ProductService;
import com.mediasoft.utils.DtoUtils;
import com.mediasoft.utils.ModelUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@SuppressWarnings("unused")
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired

    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    @DisplayName("Test get product by id")
    void getById_WithValidId_Successful() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = ModelUtils.createProduct(id);
        when(productService.getById(any())).thenReturn(product);

        String url = "/api/products/" + UUID.randomUUID();
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    @DisplayName("Test get not existing buy id than throw Exception")
    void getById_WithNotExistingId_ThrowException() throws Exception {
        UUID id = UUID.randomUUID();
        String errorMessage =  String.format("Product with id: [%s] not found!", id);
        when(productService.getById(any())).thenThrow(new ProductNotFoundException(errorMessage));
        String url = "/api/products/" + UUID.randomUUID();
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMessage));

    }

    @Test
    @DisplayName("Test create product by valid request")
    void createProduct_WithValidRequestBody_Successful() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = ModelUtils.createProduct(id);
        ProductDto productDto = DtoUtils.createProductDto();

        when(productService.create(any())).thenReturn(product);

        String url = "/api/products";
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    @DisplayName("Test create product by not valid request")
    void createProduct_WithBadRequestBody_ThrowException() throws Exception {
        String url = "/api/products";
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1")
                )
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("Test create product by not valid request")
    void createProduct_WithNotValidRequestBody_ThrowException() throws Exception {
        String url = "/api/products";
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProductDto()))
                )
                .andExpect(status().isConflict());

    }

    @Test
    @DisplayName("Test update product with valid request")
    void updateProduct_WithValidRequestBody_Successful() throws Exception{
        UUID id = UUID.randomUUID();
        Product product = ModelUtils.createProduct(id);
        ProductDto productDto = DtoUtils.createProductDto();

        when(productService.update(any(), any())).thenReturn(product);

        String url = "/api/products/" + id;
        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    @DisplayName("Test update not existing product")
    void updateProduct_WithNotExistingId_ThrowsException() throws Exception{
        UUID id = UUID.randomUUID();
        ProductDto productDto = DtoUtils.createProductDto();

        when(productService.update(any(), any())).thenThrow(new ProductNotFoundException(
                String.format("Product with id: [%s] not found!", id)
        ));
        String url = "/api/products/" + id;
        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test update with bad request")
    void updateProduct_WithBadRequest_ThrowsException() throws Exception {
        UUID id = UUID.randomUUID();

        String url = "/api/products/" + id;
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Delete product with valid id")
    void deleteProduct_WithValidId_Successful() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = ModelUtils.createProduct(id);

        when(productService.deleteById(any())).thenReturn(product);

        String url = "/api/products/" + id;
        mockMvc.perform(delete(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    @DisplayName("Delete product with not valid id")
    void deleteProduct_WithNotExistingId_Successful() throws Exception {
        String url = "/api/products/1";
        mockMvc.perform(delete(url))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Delete not existing product")
    void deleteProduct_WithNotExistingId_ThrowException() throws Exception {
        UUID id = UUID.randomUUID();

        when(productService.deleteById(any())).thenThrow(new ProductNotFoundException(
                String.format("Product with id: [%s] not found!", id)
                ));

        String url = "/api/products/" + id;
        mockMvc.perform(delete(url))
                .andExpect(status().isNotFound());
    }



}

