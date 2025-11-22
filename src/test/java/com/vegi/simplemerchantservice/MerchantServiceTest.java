package com.vegi.simplemerchantservice;


import com.vegi.simplemerchantservice.dto.MerchantResponse;
import com.vegi.simplemerchantservice.exception.MerchantNotFoundException;
import com.vegi.simplemerchantservice.model.Merchant;
import com.vegi.simplemerchantservice.model.MerchantAccount;
import com.vegi.simplemerchantservice.model.MerchantStatus;
import com.vegi.simplemerchantservice.repository.MerchantAccountRepository;
import com.vegi.simplemerchantservice.repository.MerchantCreditLogRepository;
import com.vegi.simplemerchantservice.repository.MerchantRepository;
import com.vegi.simplemerchantservice.service.MerchantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MerchantServiceTest {

    private MerchantRepository merchantRepository;
    private MerchantAccountRepository merchantAccountRepository;
    private MerchantCreditLogRepository merchantCreditLogRepository;

    private MerchantService merchantService;

    @BeforeEach
    void setUp() {
        merchantRepository = Mockito.mock(MerchantRepository.class);
        merchantAccountRepository = Mockito.mock(MerchantAccountRepository.class);
        merchantCreditLogRepository = Mockito.mock(MerchantCreditLogRepository.class);

        merchantService = new MerchantService(
                merchantRepository,
                merchantAccountRepository,
                merchantCreditLogRepository
        );
    }

    // 1️⃣ Test getMerchantDetails
    @Test
    void testGetMerchantDetails() {

        Merchant merchant = Merchant.builder()
                .merchantId("M123")
                .merchantName("Nike Store")
                .category("Retail")
                .currency("INR")
                .status(MerchantStatus.ACTIVE)
                .build();

        when(merchantRepository.findById("M123")).thenReturn(Optional.of(merchant));
        when(merchantAccountRepository.findByMerchantId("M123"))
                .thenReturn(Optional.of(MerchantAccount.builder()
                        .merchantId("M123")
                        .currentBalance(5000.0)
                        .build()));

        MerchantResponse res = merchantService.getMerchantDetails("M123");

        assertEquals("M123", res.getMerchantId());
        assertEquals(5000.0, res.getCurrentBalance());
    }

    // 2️⃣ Test merchant not found
    @Test
    void testMerchantNotFound() {

        when(merchantRepository.findById("M000")).thenReturn(Optional.empty());

        assertThrows(MerchantNotFoundException.class,
                () -> merchantService.getMerchantDetails("M000"));
    }

    // 3️⃣ Test credit merchant
    @Test
    void testCreditMerchantAccount() {

        Merchant merchant = Merchant.builder()
                .merchantId("M123")
                .merchantName("Nike")
                .status(MerchantStatus.ACTIVE)
                .currency("INR")
                .build();

        when(merchantRepository.findById("M123")).thenReturn(Optional.of(merchant));
        when(merchantAccountRepository.findByMerchantId("M123"))
                .thenReturn(Optional.of(MerchantAccount.builder()
                        .merchantId("M123")
                        .currentBalance(1000.0)
                        .build()));
        when(merchantCreditLogRepository.findByTransactionId(any())).thenReturn(Optional.empty());

        var req = new com.vegi.simplemerchantservice.dto.MerchantCreditRequest(
                "TXN-1001", "M123", 500.0, "INR"
        );

        var res = merchantService.creditMerchantAccount(req);

        assertEquals("TXN-1001", res.getTransactionId());
        assertEquals(500.0, res.getCreditedAmount());
        assertEquals("SUCCESS", res.getStatus());
    }
}
