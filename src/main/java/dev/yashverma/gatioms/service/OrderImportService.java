package dev.yashverma.gatioms.service;

import dev.yashverma.gatioms.dto.shopify.ShopifyOrderDTO;
import dev.yashverma.gatioms.domain.entity.ShopifyConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import dev.yashverma.gatioms.repository.ShopifyConfigRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderImportService {

    private final ShopifyConfigRepository shopifyConfigRepository;
    private final ShopifyApiClient shopifyApiClient;
    private final OrderImportHelper orderImportHelper;

    // Injecting self to call @Transactional method and ensure proxy interceptor is hit

    public Map<String, Integer> importOrders(String shopId) {
        log.info("Starting order import for shop: {}", shopId);
        
        ShopifyConfig config = shopifyConfigRepository.findByShopIdWithStore(shopId)
                .orElseThrow(() -> new RuntimeException("ShopifyConfig not found for shopId: " + shopId));

        List<ShopifyOrderDTO> orders = shopifyApiClient.fetchAllOrders(config);
        
        int imported = 0;
        int skipped = 0;

        for (ShopifyOrderDTO order : orders) {
            try {
                boolean wasImported = orderImportHelper.importSingleOrder(order, config);
                if (wasImported) {
                    imported++;
                } else {
                    skipped++;
                }
            } catch (Exception e) {
                log.error("Failed to import order ID: {}. Error: {}", order.getId(), e.getMessage(), e);
                skipped++;
            }
        }

        config.setLastOrderSyncDate(LocalDateTime.now());
        shopifyConfigRepository.save(config);

        Map<String, Integer> result = new HashMap<>();
        result.put("imported", imported);
        result.put("skipped", skipped);
        
        log.info("Finished order import for shop: {}. Imported: {}, Skipped: {}", shopId, imported, skipped);
        return result;
    }
}
