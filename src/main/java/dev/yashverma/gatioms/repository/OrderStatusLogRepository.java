package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.OrderStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, Long> {
}
