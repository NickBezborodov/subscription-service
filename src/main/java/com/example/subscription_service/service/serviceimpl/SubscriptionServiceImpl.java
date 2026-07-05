package com.example.subscription_service.service.serviceimpl;

import com.example.subscription_service.dto.SubscriptionDto;
import com.example.subscription_service.enums.SubscriptionType;
import com.example.subscription_service.exception.SubscriptionNotFoundException;
import com.example.subscription_service.mapper.SubscriptionMapper;
import com.example.subscription_service.model.Subscription;
import com.example.subscription_service.repository.SubscriptionRepository;
import com.example.subscription_service.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper mapper;

    @Override
    public ResponseEntity<SubscriptionDto> getSubscription(String login) {
        return subscriptionRepository.findById(login)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new SubscriptionNotFoundException("Подписка не найдена: " + login));    }

    @Override
    public ResponseEntity<SubscriptionDto> updateSubscription(String login, SubscriptionType newType) {
        Subscription subscription = subscriptionRepository.findById(login)
                .orElseThrow(() -> new SubscriptionNotFoundException("Подписка не найдена: " + login));

        subscription.setType(newType);
        subscription.setExpirationDate(LocalDate.now().plusMonths(1));

        Subscription updated = subscriptionRepository.save(subscription);
        return ResponseEntity.ok(mapper.toDto(updated));
    }
}
