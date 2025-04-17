package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.BillSummaryDto;

public interface BillQueryService {
    BillSummaryDto getBillSummary(Long subscriberNo, String month, int year);
}