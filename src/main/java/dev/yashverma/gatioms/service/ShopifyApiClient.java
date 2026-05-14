package dev.yashverma.gatioms.service;

import dev.yashverma.gatioms.domain.entity.ShopifyConfig;
import dev.yashverma.gatioms.dto.shopify.ShopifyOrderDTO;
import dev.yashverma.gatioms.dto.shopify.ShopifyOrdersResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopifyApiClient {

    private final WebClient.Builder webClientBuilder;
    private static final String API_VERSION = "2024-01";
    private static final Pattern LINK_HEADER_PATTERN = Pattern.compile("<([^>]+)>;\\s*rel=\"([^\"]+)\"");

    public List<ShopifyOrderDTO> fetchAllOrders(ShopifyConfig config) {
        List<ShopifyOrderDTO> allOrders = new ArrayList<>();
        
        String baseUrl = "https://" + config.getShopifyUrl() + "/admin/api/" + API_VERSION;
        
        StringBuilder initialUrlBuilder = new StringBuilder(baseUrl + "/orders.json?status=any&limit=250");
        if (config.getLastOrderSyncDate() != null) {
            String isoDate = config.getLastOrderSyncDate().format(DateTimeFormatter.ISO_DATE_TIME);
            initialUrlBuilder.append("&updated_at_min=").append(isoDate);
        }
        
        String nextUrl = initialUrlBuilder.toString();
        
        WebClient webClient = webClientBuilder.build();

        while (nextUrl != null && !nextUrl.isEmpty()) {
            log.info("Fetching Shopify orders from URL: {}", nextUrl);
            
            try {
                ResponseEntity<ShopifyOrdersResponseDTO> responseEntity = webClient.get()
                        .uri(nextUrl)
                        .header("X-Shopify-Access-Token", config.getAccessToken())
                        .retrieve()
                        .toEntity(ShopifyOrdersResponseDTO.class)
                        .block();

                if (responseEntity != null && responseEntity.getBody() != null && responseEntity.getBody().getOrders() != null) {
                    List<ShopifyOrderDTO> orders = responseEntity.getBody().getOrders();
                    allOrders.addAll(orders);
                    log.info("Fetched {} orders in this page.", orders.size());
                }

                // Parse Link header for pagination
                nextUrl = null;
                if (responseEntity != null) {
                    HttpHeaders headers = responseEntity.getHeaders();
                    List<String> linkHeaders = headers.get(HttpHeaders.LINK);
                    if (linkHeaders != null && !linkHeaders.isEmpty()) {
                        for (String linkHeader : linkHeaders) {
                            nextUrl = extractNextUrl(linkHeader);
                            if (nextUrl != null) {
                                break;
                            }
                        }
                    }
                }
                
            } catch (WebClientResponseException e) {
                if (e.getStatusCode().value() == 429) {
                    // Rate limit hit
                    String retryAfterStr = e.getHeaders().getFirst(HttpHeaders.RETRY_AFTER);
                    int retryAfterSeconds = 2; // Default to 2 seconds if header is missing
                    if (retryAfterStr != null) {
                        try {
                            retryAfterSeconds = Integer.parseInt(retryAfterStr);
                        } catch (NumberFormatException ignored) {}
                    }
                    
                    log.warn("Rate limit reached (429). Waiting for {} seconds before retrying.", retryAfterSeconds);
                    try {
                        Thread.sleep(retryAfterSeconds * 1000L);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interrupted during rate limit sleep", ie);
                    }
                    // Loop will repeat with the same nextUrl
                } else {
                    log.error("Error fetching orders from Shopify: {}", e.getMessage());
                    throw e;
                }
            }
        }
        
        log.info("Finished fetching all pages. Total orders fetched: {}", allOrders.size());
        return allOrders;
    }

    private String extractNextUrl(String linkHeader) {
        String[] links = linkHeader.split(",");
        for (String link : links) {
            Matcher matcher = LINK_HEADER_PATTERN.matcher(link);
            if (matcher.find()) {
                String url = matcher.group(1);
                String rel = matcher.group(2);
                if ("next".equals(rel)) {
                    return url;
                }
            }
        }
        return null;
    }
}
