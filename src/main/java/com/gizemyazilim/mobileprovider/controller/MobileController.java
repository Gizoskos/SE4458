package com.gizemyazilim.mobileprovider.controller;

import com.gizemyazilim.mobileprovider.dto.*;
import com.gizemyazilim.mobileprovider.entity.Subscriber;
import com.gizemyazilim.mobileprovider.repository.SubscriberRepository;
import com.gizemyazilim.mobileprovider.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MobileController {

    private final UsageService usageService;
    private final BillingService billingService;
    private final BillQueryService billQueryService;
    private final BillDetailService billDetailService;
    private final PaymentService paymentService;
    private final SubscriberRepository subscriberRepository;

    public MobileController(UsageService usageService,
                            BillingService billingService,
                            BillQueryService billQueryService,
                            BillDetailService billDetailService,
                            PaymentService paymentService,
                            SubscriberRepository subscriberRepository) {
        this.usageService = usageService;
        this.billingService = billingService;
        this.billQueryService = billQueryService;
        this.billDetailService = billDetailService;
        this.paymentService = paymentService;
        this.subscriberRepository = subscriberRepository;
    }

    // 🔓 No Auth
    @GetMapping("/bill")
    public ResponseEntity<BillSummaryDto> getBillSummary(@RequestParam Long subscriberNo,
                                                         @RequestParam String month,
                                                         @RequestParam int year) {
        return ResponseEntity.ok(billQueryService.getBillSummary(subscriberNo, month, year));
    }

    // 🔐 Requires Auth + Paging
    @GetMapping("/bill-detailed")
    public ResponseEntity<Page<BillDetailedDto>> getBillDetails(@RequestParam String month,
                                                                @RequestParam int year,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Subscriber subscriber = subscriberRepository.findByAppUserUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Bu kullanıcıya ait abone bulunamadı"));

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(billDetailService.getBillDetails(subscriber.getId(), month, year, pageable));
    }

    // 🔓 No Auth

    @PostMapping("/pay")
    public ResponseEntity<String> payBill(@RequestBody PayBillRequestDto dto) {
        return ResponseEntity.ok(paymentService.payBill(dto));
    }

    // 🔐 Requires Auth
    @PostMapping("/billing/calculate")
    public ResponseEntity<String> calculateBill(@RequestBody CalculateBillRequestDto dto) {
        billingService.calculate(dto);
        return ResponseEntity.ok("Bill calculated");
    }

    // 🔐 Requires Auth
    @PostMapping("/usage")
    public ResponseEntity<String> addUsage(@RequestBody AddUsageRequestDto dto) {
        usageService.addUsage(dto);
        return ResponseEntity.ok("Usage added successfully");
    }
}
