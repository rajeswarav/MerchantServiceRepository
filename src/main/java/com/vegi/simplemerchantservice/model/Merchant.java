package com.vegi.simplemerchantservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "merchant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Merchant {

    @Id
    @Column(name = "merchant_id", nullable = false, length = 36)
    private String merchantId;

    @Column(name = "merchant_name", nullable = false, length = 200)
    private String merchantName;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private MerchantStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
