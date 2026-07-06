package com.example.subscription_service.job;

import com.example.subscription_service.service.SubscriptionExpirationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionScheduler {

    private final SubscriptionExpirationService expirationService;

    @Scheduled(cron = "${app.scheduler.subscription-cron}")
    @SchedulerLock(
            name = "checkExpiredSubscriptions",
            lockAtMostFor = "5m",
            lockAtLeastFor = "1m"
    )
    public void checkAndExpireSubscriptions() {
        log.info("⏰ Запуск Scheduler'а для проверки истекших подписок");

        try {
            int processed = expirationService.expirePaidSubscriptions();
            log.info("✅ Scheduler завершил работу. Обработано: {}", processed);
        } catch (Exception e) {
            log.error("❌ Ошибка в Scheduler'е: {}", e.getMessage(), e);
        }
    }
}