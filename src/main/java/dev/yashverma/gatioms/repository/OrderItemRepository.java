package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.OrderItem;
import dev.yashverma.gatioms.domain.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT i FROM OrderItem i JOIN FETCH i.orderHeader JOIN FETCH i.product WHERE i.orderHeader.orderId = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") String orderId);

    @Transactional(readOnly = true)
    @Query(value = "SELECT i FROM OrderItem i JOIN FETCH i.orderHeader JOIN FETCH i.product WHERE i.product.productId = :productId",
           countQuery = "SELECT count(i) FROM OrderItem i WHERE i.product.productId = :productId")
    Page<OrderItem> findByProductId(@Param("productId") String productId, Pageable pageable);
}
