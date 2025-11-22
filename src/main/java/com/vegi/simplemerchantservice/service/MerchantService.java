package com.vegi.simplemerchantservice.service;


import com.vegi.simplemerchantservice.controller.MerchantController;
import com.vegi.simplemerchantservice.dto.MerchantCreateRequest;
import com.vegi.simplemerchantservice.dto.MerchantCreditRequest;
import com.vegi.simplemerchantservice.dto.MerchantCreditResponse;
import com.vegi.simplemerchantservice.dto.MerchantResponse;
import com.vegi.simplemerchantservice.model.*;
import com.vegi.simplemerchantservice.repository.MerchantAccountRepository;
import com.vegi.simplemerchantservice.repository.MerchantCreditLogRepository;
import com.vegi.simplemerchantservice.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    private final MerchantAccountRepository merchantAccountRepository;
    private final MerchantCreditLogRepository merchantCreditLogRepository;


    Logger log = LoggerFactory.getLogger(MerchantController.class);

    public MerchantResponse getMerchantDetails(String merchantId) {

        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found: " + merchantId));

        log.info("Merchant details found: " + merchant);

        return MerchantResponse.builder()
                .merchantId(merchant.getMerchantId())
                .merchantName(merchant.getMerchantName())
                .category(merchant.getCategory())
                .currency(merchant.getCurrency())
                .status(merchant.getStatus().name())
                .currentBalance(0.0)  // you can populate from merchant_account table
                .build();
    }

    public MerchantResponse createMerchant(MerchantCreateRequest request) {

        // 1. Validate: merchant already exists
        if (merchantRepository.existsById(request.getMerchantId())) {
            throw new RuntimeException("Merchant already exists with ID: " + request.getMerchantId());
        }

        // 2. Create Merchant entity
        Merchant merchant = Merchant.builder()
                .merchantId(request.getMerchantId())
                .merchantName(request.getMerchantName())
                .category(request.getCategory())
                .currency(request.getCurrency())
                .status(MerchantStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 3. Save merchant to DB
        merchantRepository.save(merchant);

        // 4. Build response object
        return MerchantResponse.builder()
                .merchantId(merchant.getMerchantId())
                .merchantName(merchant.getMerchantName())
                .category(merchant.getCategory())
                .status(merchant.getStatus().name())
                .currency(merchant.getCurrency())
                .currentBalance(0.0)  // this will be fetched from merchant_account table later
                .build();
    }


    public MerchantCreditResponse creditMerchantAccount(MerchantCreditRequest request) {
log.info("Merchant service start.....");
        // 1. Validate merchant exists
        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(() -> new RuntimeException("Merchant not found: " + request.getMerchantId()));

        // 2. Validate merchant is ACTIVE
        if (merchant.getStatus() != MerchantStatus.ACTIVE) {
            throw new RuntimeException("Merchant is not active: " + merchant.getMerchantId());
        }

        log.info("------22222222222222--------");

        // 3. Idempotency check
        Optional<MerchantCreditLog> existingLog =
                merchantCreditLogRepository.findByTransactionId(request.getTransactionId());

        if (existingLog.isPresent()) {
            // Already credited → return success without re-crediting
            return MerchantCreditResponse.builder()
                    .transactionId(existingLog.get().getTransactionId())
                    .merchantId(existingLog.get().getMerchantId())
                    .creditedAmount(existingLog.get().getCreditedAmount())
                    .status("SUCCESS")
                    .build();
        }
        log.info("------33333--------");
        // 4. Fetch merchant account balance
        MerchantAccount account = merchantAccountRepository
                .findByMerchantId(request.getMerchantId())
                .orElse(
                        // If not available, create new account with 0 balance
                        MerchantAccount.builder()
                                .merchantId(request.getMerchantId())
                                .currentBalance(0.0)
                                .lastUpdated(LocalDateTime.now())
                                .build()
                );

        // 5. Increase merchant balance
        Double newBalance = account.getCurrentBalance() + request.getAmount();
        account.setCurrentBalance(newBalance);
        account.setLastUpdated(LocalDateTime.now());
        merchantAccountRepository.save(account);

        log.info("------4444444444--------");

        // 6. Insert into merchant_credit_log
        MerchantCreditLog log = MerchantCreditLog.builder()
                .transactionId(request.getTransactionId())
                .merchantId(request.getMerchantId())
                .creditedAmount(request.getAmount())
                .currency(request.getCurrency())
                .createdAt(LocalDateTime.now())
                .build();

        merchantCreditLogRepository.save(log);

        // 7. Return Success
        return MerchantCreditResponse.builder()
                .transactionId(request.getTransactionId())
                .merchantId(request.getMerchantId())
                .creditedAmount(request.getAmount())
                .status("SUCCESS")
                .build();



    }










}
