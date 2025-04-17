package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.PayBillRequestDto;

public interface PaymentService {
    String payBill(PayBillRequestDto dto);
}