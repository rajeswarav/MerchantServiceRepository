package com.vegi.simplemerchantservice.dto;


import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class MerchantResponse {

    private String merchantId;
    private String merchantName;
    private String category;
    private String status;
    private Double currentBalance;
    private String currency;

}
