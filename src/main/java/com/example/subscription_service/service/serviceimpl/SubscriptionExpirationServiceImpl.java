package com.example.subscription_service.service.serviceimpl;

import com.example.subscription_service.dto.SubscriptionChangeEvent;
import com.example.subscription_service.enums.SubscriptionType;
import com.example.subscription_service.kafka.producer.SubscriptionEventProducer;
import com.example.subscription_service.model.Subscription;
import com.example.subscription_service.repository.SubscriptionRepository;
import com.example.subscription_service.service.SubscriptionExpirationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionExpirationServiceImpl implements SubscriptionExpirationService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionEventProducer eventProducer;

    @Transactional
    public int expirePaidSubscriptions() {
        log.info("⏰ Проверка истекших подписок");

        LocalDate now = LocalDate.now();
        List<Subscription> expiredSubscriptions = subscriptionRepository
                .findAllByTypeAndExpirationDateBefore(SubscriptionType.PAID, now);

        if (expiredSubscriptions.isEmpty()) {
            log.info("✅ Истекших платных подписок не найдено");
            return 0;
        }

        log.info("📊 Найдено {} истекших подписок", expiredSubscriptions.size());

        int processed = 0;
        for (Subscription subscription : expiredSubscriptions) {
            try {
                String login = subscription.getLogin();
                String oldType = subscription.getType().name();

                subscription.setType(SubscriptionType.FREE);
                subscriptionRepository.save(subscription);

                log.info("🔄 Подписка изменена: {} ({} → FREE)", login, oldType);

                SubscriptionChangeEvent event = new SubscriptionChangeEvent(
                        login,
                        oldType,
                        SubscriptionType.FREE.name(),
                        LocalDateTime.now()
                );
                eventProducer.sendSubscriptionChangeEvent(event);

                processed++;

            } catch (Exception e) {
                log.error("❌ Ошибка при обработке подписки {}: {}",
                        subscription.getLogin(), e.getMessage());
            }
        }

        log.info("✅ Обработано {} истекших подписок", processed);
        return processed;
    }
}
