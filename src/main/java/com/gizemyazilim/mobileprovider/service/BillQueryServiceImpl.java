package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.BillSummaryDto;
import com.gizemyazilim.mobileprovider.entity.Bill;
import com.gizemyazilim.mobileprovider.entity.Subscriber;
import com.gizemyazilim.mobileprovider.repository.BillRepository;
import com.gizemyazilim.mobileprovider.repository.SubscriberRepository;
import com.gizemyazilim.mobileprovider.service.BillQueryService;
import org.springframework.stereotype.Service;

@Service
public class BillQueryServiceImpl implements BillQueryService {

    private final SubscriberRepository subscriberRepository;
    private final BillRepository billRepository;

    public BillQueryServiceImpl(SubscriberRepository subscriberRepository, BillRepository billRepository) {
        this.subscriberRepository = subscriberRepository;
        this.billRepository = billRepository;
    }

    @Override
    public BillSummaryDto getBillSummary(Long subscriberNo, String month, int year) {
        Subscriber subscriber = subscriberRepository.findById(subscriberNo)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        Bill bill = billRepository.findBySubscriberAndMonthAndYear(subscriber, month, year)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        return new BillSummaryDto(bill.getTotalAmount(), bill.isPaid());
    }
}