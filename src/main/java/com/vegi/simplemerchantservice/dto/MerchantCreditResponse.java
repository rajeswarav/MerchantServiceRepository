package com.vegi.simplemerchantservice.dto;


import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class MerchantCreditResponse {
    private String transactionId;
    private String merchantId;
    private Double creditedAmount;
    private String status;
}
