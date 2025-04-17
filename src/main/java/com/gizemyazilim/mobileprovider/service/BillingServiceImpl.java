package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.CalculateBillRequestDto;
import com.gizemyazilim.mobileprovider.entity.*;
import com.gizemyazilim.mobileprovider.repository.*;
import com.gizemyazilim.mobileprovider.service.BillingService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    private final SubscriberRepository subscriberRepository;
    private final UsageRepository usageRepository;
    private final BillRepository billRepository;

    public BillingServiceImpl(BillRepository billRepository,
                              UsageRepository usageRepository,
                              SubscriberRepository subscriberRepository) {
        this.billRepository = billRepository;
        this.usageRepository = usageRepository;
        this.subscriberRepository = subscriberRepository;
    }
    @Override
    public void calculate(CalculateBillRequestDto dto) {
        Subscriber subscriber = subscriberRepository.findById(dto.getSubscriberNo())
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        List<Usage> usages = usageRepository.findBySubscriberAndMonthAndYear(subscriber, dto.getMonth(), dto.getYear());

        int phoneTotal = usages.stream().filter(u -> u.getType() == UsageType.PHONE).mapToInt(Usage::getAmount).sum();
        int internetTotal = usages.stream().filter(u -> u.getType() == UsageType.INTERNET).mapToInt(Usage::getAmount).sum();

        double total = 0.0;

        if (phoneTotal > 1000) {
            int extraMinutes = phoneTotal - 1000;
            int extraBlocks = (int) Math.ceil(extraMinutes / 1000.0);
            total += extraBlocks * 10.0;
        }

        if (internetTotal > 0) {
            if (internetTotal <= 20480) {
                total += 50.0;
            } else {
                int extraMb = internetTotal - 20480;
                int extraBlocks = (int) Math.ceil(extraMb / 10240.0);
                total += 50.0 + extraBlocks * 10.0;
            }
        }

        Bill bill = billRepository.findBySubscriberAndMonthAndYear(subscriber, dto.getMonth(), dto.getYear())
                .orElse(new Bill());

        bill.setSubscriber(subscriber);
        bill.setMonth(dto.getMonth());
        bill.setYear(dto.getYear());
        bill.setPhoneMinutes(phoneTotal);
        bill.setInternetMb(internetTotal);
        bill.setTotalAmount(total);
        bill.setPaid(false);

        billRepository.save(bill);
    }
}
