package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.BillDetailedDto;
import com.gizemyazilim.mobileprovider.entity.Subscriber;
import com.gizemyazilim.mobileprovider.entity.Usage;
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

    public BillDetailServiceImpl(SubscriberRepository subscriberRepository, UsageRepository usageRepository) {
        this.subscriberRepository = subscriberRepository;
        this.usageRepository = usageRepository;
    }


    @Override
    public Page<BillDetailedDto> getBillDetails(Long subscriberNo, String month, int year, Pageable pageable) {
        Subscriber subscriber = subscriberRepository.findById(subscriberNo)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        List<Usage> usages = usageRepository.findBySubscriberAndMonthAndYear(subscriber, month, year);

        List<BillDetailedDto> dtos = usages.stream()
                .map(u -> new BillDetailedDto(u.getType().toString(), u.getAmount()))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        List<BillDetailedDto> pageContent = dtos.subList(start, end);

        return new PageImpl<>(pageContent, pageable, dtos.size());
    }
}