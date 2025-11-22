package com.example.demo.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Setter
@Getter
public class PaymentRequest {

    @NotNull(message = "Product is required")
    @NotBlank(message = "Should not be blank")
    public String product;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be at least 1")
    public Long price;

    @NotNull(message = "Customer is required")
    @NotBlank(message = "Should not be blank")
    public String customer;

}