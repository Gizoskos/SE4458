package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.AddUsageRequestDto;
import com.gizemyazilim.mobileprovider.entity.*;
import com.gizemyazilim.mobileprovider.repository.*;
import com.gizemyazilim.mobileprovider.service.UsageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsageServiceImpl implements UsageService {

    private final SubscriberRepository subscriberRepository;
    private final UsageRepository usageRepository;
    public UsageServiceImpl(SubscriberRepository subscriberRepository, UsageRepository usageRepository) {
        this.subscriberRepository = subscriberRepository;
        this.usageRepository = usageRepository;
    }
    @Override
    public void addUsage(AddUsageRequestDto dto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Subscriber subscriber = subscriberRepository.findByAppUserUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("No subscribers found for this user"));

        Usage usage = new Usage();
        usage.setSubscriber(subscriber);
        usage.setMonth(dto.getMonth());
        usage.setYear(dto.getYear());
        usage.setType(UsageType.valueOf(dto.getUsageType().toUpperCase()));
        usage.setAmount(dto.getAmount());

        usageRepository.save(usage);
    }
}
