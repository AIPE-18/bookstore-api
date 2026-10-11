package com.example.bookstore.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record BookRequest(

    @NotBlank(message = "Title is required")
    String title,

    @NotBlank(message = "Author is required")
    String author,

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 20, message = "ISBN must be 10-20 characters")
    String isbn,

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    BigDecimal price,

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    Integer stock
) {}