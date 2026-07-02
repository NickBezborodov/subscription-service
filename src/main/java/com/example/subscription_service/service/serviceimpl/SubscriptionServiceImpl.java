package com.example.subscription_service.service.serviceimpl;

import com.example.subscription_service.enums.SubscriptionType;
import com.example.subscription_service.model.Subscription;
import com.example.subscription_service.repository.SubscriptionRepository;
import com.example.subscription_service.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Subscription getSubscription(String login) {
        return null;
    }

    @Override
    public Subscription updateSubscription(String login, SubscriptionType newType) {
        return null;
    }

    @Override
    public void invalidateCache(String login) {

    }

    private void saveToCache(Subscription subscription) {
        String key = "subscription:" + subscription.getLogin();
        redisTemplate.opsForValue().set(key, subscription);
    }
}
