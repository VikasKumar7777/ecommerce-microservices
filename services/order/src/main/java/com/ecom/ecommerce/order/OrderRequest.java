package com.ecom.ecommerce.order;

import com.ecom.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String refrence,
        @Positive(message = "order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "payment method is required")
        PaymentMethod paymentMethod,
        @NotNull(message = "customer should be present")
        @NotEmpty(message = "customer should be present")
        @NotBlank(message = "customer should be present")
        String customerId,

        @NotEmpty(message = "product is missing")
        List<PurchaseRequest> products
) {
}
