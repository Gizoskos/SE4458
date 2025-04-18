package com.gizemyazilim.mobileprovider.service;

import com.gizemyazilim.mobileprovider.dto.PayBillRequestDto;
import com.gizemyazilim.mobileprovider.entity.Bill;
import com.gizemyazilim.mobileprovider.entity.Subscriber;
import com.gizemyazilim.mobileprovider.repository.BillRepository;
import com.gizemyazilim.mobileprovider.repository.SubscriberRepository;
import com.gizemyazilim.mobileprovider.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> response = new HashMap<>();

        if (dto.getAmount() <= 0) {
            response.put("paymentStatus", "ERROR");
            response.put("isPaid", false);
            response.put("message", "Payment amount must be greater than zero.");
            return response.toString();
        }

        Subscriber subscriber = subscriberRepository.findById(dto.getSubscriberNo())
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        Bill bill = billRepository.findBySubscriberAndMonthAndYear(subscriber, dto.getMonth(), dto.getYear())
                .orElseThrow(() -> new RuntimeException("Bill not found"));


        if (bill.isPaid()) {
            response.put("paymentStatus", "ALREADY_PAID");
            response.put("isPaid", true);
            response.put("message", "Bill already paid.");
            return response.toString();
        }

        double remainingAmount = bill.getTotalAmount() - dto.getAmount();

        if (remainingAmount <= 0) {
            bill.setTotalAmount(0.0);
            bill.setPaid(true);
            response.put("paymentStatus", "SUCCESS");
            response.put("isPaid", true);
        } else {
            bill.setTotalAmount(remainingAmount);
            bill.setPaid(false);
            response.put("paymentStatus", "PARTIAL");
            response.put("isPaid", false);
        }

        billRepository.save(bill);
        return response.toString();
    }
}
