# ROADMAP.md — GatiOMS Implementation Plan

## Sprint 1: Foundation (Entities → Schema → Seed Data)
- [ ] **Step 1.1**: Define `OrderStatus.java` enum with state transition logic.
- [ ] **Step 1.2**: Implement all 16 JPA @Entity classes with strict Lombok rules.
- [ ] **Step 1.3**: Configure `application.yml` for `ddl-auto: create`.
- [ ] **Step 1.4**: Capture Hibernate generated DDL and clean for Flyway.
- [ ] **Step 1.5**: Create `V1__Initial_Setup.sql` (Schema) and `V2__Reference_Data.sql` (Seed).
- [ ] **Step 1.6**: Enable Flyway and switch to `ddl-auto: validate`.

## Sprint 2: Data Access Layer
- [ ] JPA Repository interfaces for all mandatory entities.
- [ ] Repository integration testing using H2 (In-memory).
- [ ] Implementation of `OrderStatusLog` persistence logic.

## Sprint 3: Core Order Service
- [ ] `OrderService`: Main business logic for CRUD operations.
- [ ] `OrderStatusService`: State transition validation using the `OrderStatus` enum.
- [ ] `GlobalExceptionHandler` and custom `BusinessException` framework.
- [ ] Unit testing services with Mockito.

## Sprint 4: Shopify Integration
- [ ] `ShopifyApiClient`: Type-safe WebClient wrapper for Shopify REST API.
- [ ] `ShopifyProductSyncJob`: Daily job to sync Shopify Products/Variants to internal catalog.
- [ ] `ShopifyOrderSyncJob`: 5-minute poll for new orders with deduplication guard.
- [ ] Manual trigger endpoints for synchronization.

## Sprint 5: REST API Layer
- [ ] `OrderController`: API endpoints for order management.
- [ ] `ProductController`: Catalog visibility.
- [ ] Final README.md and deployment guide.
