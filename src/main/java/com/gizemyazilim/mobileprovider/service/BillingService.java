package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.CalculateBillRequestDto;

public interface BillingService {
    void calculate(CalculateBillRequestDto dto);
}
