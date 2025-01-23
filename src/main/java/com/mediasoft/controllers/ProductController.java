package com.mediasoft.controllers;

import com.mediasoft.controllers.dtos.ProductDto;
import com.mediasoft.models.Product;
import com.mediasoft.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "ProductController")
public class ProductController {

    private final ProductService productsService;

    @Operation(description = "Endpoint that returns products by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success| OK"),
            @ApiResponse(responseCode = "404", description = "Product Not Found!"),
            @ApiResponse(responseCode = "400", description = "Bad request!")
    })
    @GetMapping("/{productID}")
    public ResponseEntity<Product> getById(
            @Parameter(description = "Product ID")
            @PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(productsService.getById(productID));
    }


    @Operation(description = "Endpoint that returns list of all existing products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success | OK")
    })
    @GetMapping
    public ResponseEntity<Collection<Product>> getAll() {
        return ResponseEntity.ok(productsService.getAll());

    }

    @Operation(description = "Endpoint that creates product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success | Created"),
            @ApiResponse(responseCode = "409", description = "Conflict create"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<Product> create(
            @Parameter(description = "Product information needed for creation")
            @Validated
            @RequestBody ProductDto dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.create(dto));
    }

    @Operation(description = "Endpoint that updates product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success | Updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "409", description = "Conflict update"),
    })
    @PutMapping("/{productID}")

    public ResponseEntity<Product> update(
            @Parameter(description = "Product Id")
            @PathVariable("productID") UUID productID,
            @Parameter(description = "Product information needed for updated")
            @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productsService.update(dto, productID));

    }

    @Operation(description = "Endpoint that deletes product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success | Deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found!"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })

    @DeleteMapping("/{productID}")
    public ResponseEntity<Product> delete(
            @Parameter(description = "Product id")
            @PathVariable("productID") UUID productID) {
        return ResponseEntity.ok(productsService.deleteById(productID));
    }

}

