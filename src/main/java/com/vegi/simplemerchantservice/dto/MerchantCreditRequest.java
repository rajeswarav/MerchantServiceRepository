package com.vegi.simplemerchantservice.dto;


import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class MerchantCreditRequest {
    private String transactionId;
    private String merchantId;
    private Double amount;
    private String currency;
}
