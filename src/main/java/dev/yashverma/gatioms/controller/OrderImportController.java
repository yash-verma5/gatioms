package dev.yashverma.gatioms.controller;

import dev.yashverma.gatioms.service.OrderImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderImportController {

    private final OrderImportService orderImportService;

    @PostMapping("/import")
    public ResponseEntity<Map<String, Object>> importOrders(@RequestParam String shopId) {
        log.info("Received request to import orders for shopId: {}", shopId);
        
        try {
            Map<String, Integer> importResult = orderImportService.importOrders(shopId);
            
            return ResponseEntity.ok(Map.of(
                    "imported", importResult.get("imported"),
                    "skipped", importResult.get("skipped"),
                    "status", "SUCCESS",
                    "shopId", shopId
            ));
        } catch (Exception e) {
            log.error("Failed to import orders for shopId: {}", shopId, e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "ERROR",
                    "message", e.getMessage(),
                    "shopId", shopId
            ));
        }
    }
}
