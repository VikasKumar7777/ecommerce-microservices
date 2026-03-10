package com.ecom.ecommerce.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,

        Integer orderId,
        @NotNull(message = "product is required")
        Integer productId,
        @Positive(message = "Quantity is mandatory")
        double quantity) {
}
