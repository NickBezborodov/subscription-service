package com.example.subscription_service.mapper;

import com.example.subscription_service.dto.SubscriptionDto;
import com.example.subscription_service.model.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    public SubscriptionDto toDto(Subscription subscription) {
        if (subscription == null) {
            return null;
        }
        return new SubscriptionDto(
                subscription.getLogin(),
                subscription.getType(),
                subscription.getExpirationDate()
        );
    }
}
