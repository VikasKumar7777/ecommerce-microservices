package com.ecom.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Cusomer firstname is required")
        String firstname,
        @NotNull(message = "Cusomer lastname is required")
        String lastname,
        @NotNull(message = "Cusomer email is required")
        @Email(message = "Email should be valid")
        String email,
        Address address
) {

}
