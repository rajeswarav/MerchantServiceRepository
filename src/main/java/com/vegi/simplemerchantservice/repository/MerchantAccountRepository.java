package com.vegi.simplemerchantservice.repository;


import com.vegi.simplemerchantservice.model.MerchantAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantAccountRepository extends JpaRepository<MerchantAccount, Long> {
    Optional<MerchantAccount> findByMerchantId(String merchantId);
}
