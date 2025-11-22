package com.vegi.simplemerchantservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantCreateRequest {

    @NotNull(message = "Merchant ID cannot be null")
    @Size(min = 1, message = "Merchant ID cannot be empty")
    private String merchantId;
    @NotBlank(message = "Merchant name is required")
    private String merchantName;
    @NotBlank(message = "Category cannot be empty")
    private String category;
    @NotBlank(message = "Currency is required")
    private String currency;  // Example: "INR"
}
