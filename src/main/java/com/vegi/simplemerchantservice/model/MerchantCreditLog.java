package com.vegi.simplemerchantservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "merchant_credit_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantCreditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;   // UNIQUE for idempotency

    private String merchantId;

    private Double creditedAmount;

    private String currency;

    private LocalDateTime createdAt;
}

