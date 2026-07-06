package com.example.subscription_service.mapper;

import com.example.subscription_service.dto.SubscriptionDto;
import com.example.subscription_service.model.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionDto toDto(Subscription subscription);
}