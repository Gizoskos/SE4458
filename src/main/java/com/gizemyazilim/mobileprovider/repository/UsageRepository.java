package com.gizemyazilim.mobileprovider.repository;

import com.gizemyazilim.mobileprovider.entity.Subscriber;
import com.gizemyazilim.mobileprovider.entity.Usage;
import com.gizemyazilim.mobileprovider.entity.UsageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsageRepository extends JpaRepository<Usage, Long> {
    List<Usage> findBySubscriberAndMonthAndYear(Subscriber subscriber, String month, int year);

    Page<Usage> findAllBySubscriberAndMonthAndYear(Subscriber subscriber, String month, int year, Pageable pageable);

    long countBySubscriberAndMonthAndYearAndType(Subscriber subscriber, String month, int year, UsageType type);
}
