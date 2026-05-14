package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, String> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT o FROM OrderHeader o JOIN FETCH o.customerParty JOIN FETCH o.productStore WHERE o.statusId = :statusId",
           countQuery = "SELECT count(o) FROM OrderHeader o WHERE o.statusId = :statusId")
    Page<OrderHeader> findByStatusId(@Param("statusId") String statusId, Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "SELECT o FROM OrderHeader o JOIN FETCH o.customerParty JOIN FETCH o.productStore WHERE o.customerParty.partyId = :partyId",
           countQuery = "SELECT count(o) FROM OrderHeader o WHERE o.customerParty.partyId = :partyId")
    Page<OrderHeader> findByCustomerPartyId(@Param("partyId") String partyId, Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "SELECT o FROM OrderHeader o JOIN FETCH o.customerParty JOIN FETCH o.productStore WHERE o.productStore.productStoreId = :storeId AND o.statusId = :statusId",
           countQuery = "SELECT count(o) FROM OrderHeader o WHERE o.productStore.productStoreId = :storeId AND o.statusId = :statusId")
    Page<OrderHeader> findByProductStoreIdAndStatusId(@Param("storeId") String storeId, @Param("statusId") String statusId, Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "SELECT o FROM OrderHeader o JOIN FETCH o.customerParty JOIN FETCH o.productStore WHERE o.orderDate BETWEEN :from AND :to",
           countQuery = "SELECT count(o) FROM OrderHeader o WHERE o.orderDate BETWEEN :from AND :to")
    Page<OrderHeader> findByOrderDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, Pageable pageable);
}
