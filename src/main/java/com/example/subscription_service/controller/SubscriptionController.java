package com.example.subscription_service.controller;

import com.example.subscription_service.dto.SubscriptionDto;
import com.example.subscription_service.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/{login}")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable String login) {
        return subscriptionService.getSubscription(login);
    }

    @PostMapping("/{login}")
    public ResponseEntity<SubscriptionDto> createSubscription(
            @PathVariable String login,
            @RequestParam String type) {
        SubscriptionDto created = subscriptionService.createSubscription(login, type);
        return ResponseEntity.ok(created);
    }
}