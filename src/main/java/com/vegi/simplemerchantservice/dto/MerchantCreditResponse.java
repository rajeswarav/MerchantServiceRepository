package com.vegi.simplemerchantservice.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantCreditResponse {
    private String transactionId;
    private String merchantId;
    private Double creditedAmount;
    private String status;
}
