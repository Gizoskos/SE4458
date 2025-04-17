package com.gizemyazilim.mobileprovider.repository;

import com.gizemyazilim.mobileprovider.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByAppUserUsername(String username);

}
