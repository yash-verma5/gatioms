# SPEC.md — GatiOMS Project Specification

> **Status**: `DRAFT`
> **Project Name**: GatiOMS (गति — velocity, movement, flow)
> **Location**: `/home/yashverma/Ag-playground/ADOC-copy/Spring Project/gatioms/`

## Vision
Build a modern, decoupled Shopify-to-OMS order import system in Spring Boot that mirrors battle-tested enterprise architectural patterns. This project serves as a premium learning sandbox to master Spring Boot while preserving established OMS domain expertise.

## Goals
1.  **Mirror Legacy Patterns**: Map standard enterprise entities (OrderHeader, Party, Product Virtual/Variant) to Spring Data JPA.
2.  **Strict Data Contracts**: Use Java Records for internal and API DTOs to ensure type safety and immutability.
3.  **Decoupled Integration**: Implement an asynchronous Shopify polling mechanism using `WebClient` and `ApplicationEvents`.
4.  **Premium Design**: Ensure the codebase follows clean 3-layer architecture (Controller → Service → Repository).
5.  **Auditability**: Preserve the `OrderStatusLog` pattern for full state transition tracking.

## Non-Goals (Out of Scope v1)
- Payment Gateway Integrations (Processing/Refunds).
- Multi-currency/Multi-language localization logic.
- Real-time Webhooks (Poll-based only for v1).
- Complex Return workflows.
- Spring Batch (Standard `@Scheduled` only).

## Architecture
- **Framework**: Spring Boot 3.5.14, Java 21.
- **Data Layer**: Spring Data JPA with MySQL 8.
- **Migration**: Flyway for versioned schema management.
- **Communication**: WebFlux `WebClient` for Shopify REST API.
- **Pattern**: 3-Layer (REST → Business Service → Repository).

## Key Entities
- **Order Core**: `OrderHeader`, `OrderItem`, `OrderFulfillmentGroup` (Mapping of OFBiz ShipGroup / Moqui Part).
- **Product**: `Product` (Virtual/Variant), `ProductAssoc`, `GoodIdentification`.
- **Party**: `Party`, `Person`, `ProductStore`.
- **Integration**: `ShopifyConfig`, `ShopifyShopOrder` (Deduplication Bridge).

## Success Criteria
- [ ] Successful poll and import of Shopify orders into `OrderHeader`/`OrderItem`.
- [ ] Correct resolution of Shopify variants to internal `Product` IDs via `GoodIdentification`.
- [ ] Validated state transitions (CREATED → APPROVED → PROCESSING).
- [ ] Automated schema generation and migration via Flyway.
