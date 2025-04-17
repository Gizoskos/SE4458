package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.PayBillRequestDto;
import com.gizemyazilim.mobileprovider.entity.Bill;
import com.gizemyazilim.mobileprovider.entity.Subscriber;
import com.gizemyazilim.mobileprovider.repository.BillRepository;
import com.gizemyazilim.mobileprovider.repository.SubscriberRepository;
import com.gizemyazilim.mobileprovider.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final SubscriberRepository subscriberRepository;
    private final BillRepository billRepository;

    public PaymentServiceImpl(SubscriberRepository subscriberRepository, BillRepository billRepository) {
        this.subscriberRepository = subscriberRepository;
        this.billRepository = billRepository;
    }

    @Override
    public String payBill(PayBillRequestDto dto) {
        Subscriber subscriber = subscriberRepository.findById(dto.getSubscriberNo())
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        Bill bill = billRepository.findBySubscriberAndMonthAndYear(subscriber, dto.getMonth(), dto.getYear())
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (bill.isPaid()) {
            return "Bill already paid.";
        }

        bill.setPaid(true);
        billRepository.save(bill);

        return "Payment successful.";
    }
}
