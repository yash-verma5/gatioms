package dev.yashverma.gatioms.scheduler;

import dev.yashverma.gatioms.domain.entity.ShopifyConfig;
import dev.yashverma.gatioms.repository.ShopifyConfigRepository;
import dev.yashverma.gatioms.service.OrderImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderImportScheduler {

    private final ShopifyConfigRepository shopifyConfigRepository;
    private final OrderImportService orderImportService;

    @Scheduled(fixedDelay = 300000) // 5 minutes
    public void scheduleOrderImport() {
        log.info("Starting scheduled order import...");
        
        List<ShopifyConfig> activeConfigs = shopifyConfigRepository.findByIsActive("Y");
        
        if (activeConfigs.isEmpty()) {
            log.info("No active ShopifyConfigs found. Skipping import.");
            return;
        }

        for (ShopifyConfig config : activeConfigs) {
            try {
                Map<String, Integer> result = orderImportService.importOrders(config.getShopId());
                log.info("Scheduled import completed for shop {}. Imported: {}, Skipped: {}", 
                        config.getShopId(), result.get("imported"), result.get("skipped"));
            } catch (Exception e) {
                log.error("Scheduled import failed for shop {}", config.getShopId(), e);
            }
        }
        
        log.info("Scheduled order import finished.");
    }
}
