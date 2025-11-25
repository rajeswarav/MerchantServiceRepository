package com.vegi.simplemerchantservice.dto;


import lombok.*;


@Data
@AllArgsConstructor
@Builder
public class MerchantCreateRequest {

    private String merchantId;
    private String merchantName;
    private String category;
    private String currency;  // Example: "INR"
}
