package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, String> {

    @Transactional(readOnly = true)
    List<OrderHeader> findByStatusId(String statusId);

    @Transactional(readOnly = true)
    @Query("SELECT o FROM OrderHeader o WHERE o.customerParty.partyId = :partyId")
    List<OrderHeader> findByCustomerPartyId(@Param("partyId") String partyId);

    @Transactional(readOnly = true)
    @Query("SELECT o FROM OrderHeader o WHERE o.productStore.productStoreId = :storeId AND o.statusId = :statusId")
    List<OrderHeader> findByProductStoreIdAndStatusId(@Param("storeId") String storeId, @Param("statusId") String statusId);

    @Transactional(readOnly = true)
    List<OrderHeader> findByOrderDateBetween(LocalDateTime from, LocalDateTime to);
}
