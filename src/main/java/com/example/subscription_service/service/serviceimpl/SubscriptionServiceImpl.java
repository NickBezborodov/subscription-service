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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SubscriptionMapper mapper;

    @Override
    public ResponseEntity<SubscriptionDto> getSubscription(String login) {
        String key = "subscription:" + login;

        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            Subscription subscription = (Subscription) cached;
            return ResponseEntity.ok(mapper.toDto(subscription));
        }
        Optional<Subscription> fromDb = subscriptionRepository.findById(login);

        if (fromDb.isPresent()) {
            Subscription subscription = fromDb.get();
            saveToCache(subscription);
            return ResponseEntity.ok(mapper.toDto(subscription));
        }
        throw new SubscriptionNotFoundException("Подписка не найдена для логина: " + login);
    }

    @Override
    public ResponseEntity<SubscriptionDto> updateSubscription(String login, SubscriptionType newType) {
        Optional<Subscription> fromDb = subscriptionRepository.findById(login);
        if (fromDb.isEmpty()) {
            throw new SubscriptionNotFoundException("Подписка не найдена для логина: " + login);
        }

        Subscription subscription = fromDb.get();
        subscription.setType(newType);
        subscription.setExpirationDate(LocalDate.now().plusMonths(1));

        Subscription updated = subscriptionRepository.save(subscription);
        invalidateCache(login);
        saveToCache(updated);
        return ResponseEntity.ok(mapper.toDto(updated));
    }

    @Override
    public void invalidateCache(String login) {
        String key = "subscription:" + login;
        redisTemplate.delete(key);
    }

    private void saveToCache(Subscription subscription) {
        String key = "subscription:" + subscription.getLogin();
        redisTemplate.opsForValue().set(key, subscription);
    }
}
