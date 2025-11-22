package com.vegi.simplemerchantservice.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantCreditRequest {
    private String transactionId;
    private String merchantId;
    private Double amount;
    private String currency;
}
