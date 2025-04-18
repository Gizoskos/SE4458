package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.BillDetailedDto;
import com.gizemyazilim.mobileprovider.entity.Bill;
import com.gizemyazilim.mobileprovider.entity.Subscriber;
import com.gizemyazilim.mobileprovider.entity.Usage;
import com.gizemyazilim.mobileprovider.repository.BillRepository;
import com.gizemyazilim.mobileprovider.repository.SubscriberRepository;
import com.gizemyazilim.mobileprovider.repository.UsageRepository;
import com.gizemyazilim.mobileprovider.service.BillDetailService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillDetailServiceImpl implements BillDetailService {

    private final SubscriberRepository subscriberRepository;
    private final UsageRepository usageRepository;
    private final BillRepository billRepository;

    public BillDetailServiceImpl(SubscriberRepository subscriberRepository, UsageRepository usageRepository, BillRepository billRepository) {
        this.subscriberRepository = subscriberRepository;
        this.usageRepository = usageRepository;
        this.billRepository = billRepository;
    }


    @Override
    public Page<BillDetailedDto> getBillDetails(Long subscriberNo, String month, int year, Pageable pageable) {
        Subscriber subscriber = subscriberRepository.findById(subscriberNo)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        List<Usage> usages = usageRepository.findBySubscriberAndMonthAndYear(subscriber, month, year);
        Bill bill = billRepository.findBySubscriberAndMonthAndYear(subscriber, month, year)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        List<BillDetailedDto> dtos = usages.stream()
                .map(u -> {
                    String unit = switch (u.getType().toString()) {
                        case "PHONE" -> "minute";
                        case "INTERNET" -> "MB";
                        case "SMS" -> "count";
                        default -> "unit";
                    };
                    return new BillDetailedDto(u.getType().toString(), u.getAmount(), unit, bill.getTotalAmount());
                })
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        List<BillDetailedDto> pageContent = dtos.subList(start, end);

        return new PageImpl<>(pageContent, pageable, dtos.size());
    }
}