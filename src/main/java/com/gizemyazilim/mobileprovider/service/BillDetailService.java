package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.BillDetailedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillDetailService {
    Page<BillDetailedDto> getBillDetails(Long subscriberNo, String month, int year, Pageable pageable);
}