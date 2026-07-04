package com.example.subscription_service.service;

import com.example.subscription_service.dto.SubscriptionDto;
import com.example.subscription_service.enums.SubscriptionType;
import com.example.subscription_service.model.Subscription;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface SubscriptionService {


    ResponseEntity<SubscriptionDto> getSubscription(String login);

    ResponseEntity<SubscriptionDto> updateSubscription(String login, SubscriptionType newType);

    void invalidateCache(String login);
}
