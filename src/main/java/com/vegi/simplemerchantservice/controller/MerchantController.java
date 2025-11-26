package com.vegi.simplemerchantservice.controller;


import com.vegi.simplemerchantservice.dto.MerchantCreateRequest;
import com.vegi.simplemerchantservice.dto.MerchantCreditRequest;
import com.vegi.simplemerchantservice.dto.MerchantCreditResponse;
import com.vegi.simplemerchantservice.dto.MerchantResponse;
import com.vegi.simplemerchantservice.service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
@Tag(name = "Merchant API", description = "Operations related to merchant details")
public class MerchantController {

    private final MerchantService merchantService;

    //adding logger to log the info new change
    Logger log = LoggerFactory.getLogger(MerchantController.class);

    @Operation(summary = "Get Merchant Details",
            description = "Returns merchant basic information and current balance")
    @GetMapping("/{merchantId}")
    public MerchantResponse getMerchantDetails(@PathVariable String merchantId) {

        log.info("Cotroller calling Mechant Service:" + merchantId);
        return merchantService.getMerchantDetails(merchantId);
    }


    @PostMapping
    @Operation(summary = "Create Merchant")
    public MerchantResponse createMerchant(@RequestBody MerchantCreateRequest request) {
        return merchantService.createMerchant(request);
    }

    @PostMapping("/credit")
    @Operation(summary = "Credit Merchant Account")
    public MerchantCreditResponse creditMerchant(@RequestBody MerchantCreditRequest request) {
        log.info("Cotroller calling Mechant Service:" + request.getMerchantId());
        return merchantService.creditMerchantAccount(request);
    }


}
