package com.example.subscription_service.dto;

import com.example.subscription_service.enums.SubscriptionType;

import java.time.LocalDate;

public record SubscriptionDto(String login, SubscriptionType type, LocalDate expirationDate) {
}
