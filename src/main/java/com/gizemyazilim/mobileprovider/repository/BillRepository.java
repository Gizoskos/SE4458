package com.gizemyazilim.mobileprovider.repository;

import com.gizemyazilim.mobileprovider.entity.Bill;
import com.gizemyazilim.mobileprovider.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findBySubscriberAndMonthAndYear(Subscriber subscriber, String month, int year);
}
