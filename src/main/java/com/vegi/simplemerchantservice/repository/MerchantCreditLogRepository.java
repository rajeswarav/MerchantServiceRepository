package com.vegi.simplemerchantservice.repository;


import com.vegi.simplemerchantservice.model.MerchantCreditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantCreditLogRepository extends JpaRepository<MerchantCreditLog, Long> {
    Optional<MerchantCreditLog> findByTransactionId(String transactionId);
}
