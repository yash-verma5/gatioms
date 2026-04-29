<div align="center">

# 🚀 Gati OMS

**A Modern, Decoupled Order Management System built with Spring Boot**

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.14-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

</div>

## 📖 Overview

**Gati OMS** (from the Sanskrit *गति* meaning velocity, movement, or flow) is a sophisticated learning project designed to master Spring Boot while implementing enterprise-grade Order Management System (OMS) patterns. 

This project focuses on bridging external e-commerce platforms (like Shopify) with a robust, normalized internal OMS database structure. It serves as a sandbox to explore and implement decoupled integration architectures, strict data contracts, and solid state-transition tracking.

## ✨ Key Features

- **Standardized Enterprise Schema:** Implements battle-tested entity structures including `OrderHeader`, `Party`, `Product Virtual/Variant`, and `ContactMech`.
- **Deduplication Engine:** Built-in safeguards (`ShopifyShopOrder`) to ensure external orders are never imported twice.
- **Strict Auditing:** Utilizes `@MappedSuperclass` and JPA Auditing to automatically track `created_stamp`, `last_updated_stamp`, and the users responsible for modifications.
- **Type-Safe Contracts:** Internal and API DTOs use Java Records for guaranteed immutability and thread safety.
- **Immutable Migrations:** Database schema is version-controlled and managed entirely via Flyway migrations.

## 🏗️ Architecture Stack

* **Framework**: Spring Boot 3.5.14
* **Language**: Java 21
* **Persistence**: Spring Data JPA & Hibernate
* **Database**: MySQL 8.0
* **Schema Management**: Flyway
* **External Communication**: Spring WebFlux (`WebClient`)

## 📂 Project Structure

The project follows a clean 3-Layer Architecture:

* `dev.yashverma.gatioms.domain.entity` - JPA Entities mapping the normalized OMS schema.
* `dev.yashverma.gatioms.repository` - Spring Data JPA Repositories with optimized JPQL queries.
* `dev.yashverma.gatioms.service` - Business logic and external API orchestration (WIP).
* `dev.yashverma.gatioms.dto` - Java Records for strict data contracts.

## 🚀 Getting Started

### Prerequisites
* Java 21+
* Maven 3.9+
* MySQL 8.0+

### Setup Database
Create a MySQL database named `gatioms`:
```sql
CREATE DATABASE gatioms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Update your `src/main/resources/application.yml` with your database credentials.

### Run Migrations & Start
The application uses Flyway. On the first run, it will automatically generate the schema and seed the baseline data.
```bash
./mvnw spring-boot:run
```

---

<div align="center">
<i>Built as an exploratory sandbox for mastering scalable Spring Boot architectures.</i>
</div>
