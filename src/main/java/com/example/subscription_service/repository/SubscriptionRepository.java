package com.example.subscription_service.repository;

import com.example.subscription_service.enums.SubscriptionType;
import com.example.subscription_service.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
    List<Subscription> findAllByTypeAndExpirationDateBefore(SubscriptionType type, LocalDate date);
}
