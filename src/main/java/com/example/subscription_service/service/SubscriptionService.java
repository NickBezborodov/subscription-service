package com.example.subscription_service.service;

import com.example.subscription_service.enums.SubscriptionType;
import com.example.subscription_service.model.Subscription;

public interface SubscriptionService {


    Subscription getSubscription(String login);

    Subscription updateSubscription(String login, SubscriptionType newType);

    void invalidateCache(String login);
}
