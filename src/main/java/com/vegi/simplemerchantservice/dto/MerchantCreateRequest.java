package com.vegi.simplemerchantservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantCreateRequest {

    private String merchantId;
    private String merchantName;
    private String category;
    private String currency;  // Example: "INR"
}
