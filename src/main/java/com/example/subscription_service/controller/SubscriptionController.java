package com.example.subscription_service.controller;

import com.example.subscription_service.dto.SubscriptionDto;
import com.example.subscription_service.enums.SubscriptionType;
import com.example.subscription_service.model.Subscription;
import com.example.subscription_service.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/{login}")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable String login) {
        log.info("GET subscription: {}", login);
        return subscriptionService.getSubscription(login);
    }

    @PutMapping("/{login}")
    public ResponseEntity<SubscriptionDto> updateSubscription(
            @PathVariable String login,
            @RequestParam SubscriptionType type) {
        log.info("PUT subscription: {} -> {}", login, type);
        return subscriptionService.updateSubscription(login, type);
    }
}
