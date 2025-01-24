package com.mediasoft.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Data transfer object for product information")

public class ProductDto {

    @Schema(description = "The name of the product", example = "Iphone 13")
    @NotNull(message = "The Name of the product is mandatory")
    @NotBlank(message = "The Name of the product must not be blank")
    private String name;

    @Schema(description = "The description of the product", example = "This is Telephone for unique mans")
    @NotNull(message = "The description of the product is mandatory")
    @NotBlank(message = "The description of the product must not be blank")
    private String description;

    @Schema(description = "The category of the product", example = "mobile")
    @NotNull(message = "The category of the product is mandatory")
    @NotBlank(message = "The category of the product must not be blank")
    private String category;

    @Schema(description = "The article of the product, Need to be unique!", example = "10492049")
    @NotNull(message = "The article of the product is mandatory")
    @NotBlank(message = "The article of the product must not be blank")
    private String article;

    @Schema(description = "The quantity of the product", example = "13")
    @NotNull(message = "The quantity of the product is mandatory")
    private Integer quantity;

    @Schema(description = "The price of the product", example = "150")
    @NotNull(message = "The price of the product is mandatory")
    private BigDecimal price;
}
