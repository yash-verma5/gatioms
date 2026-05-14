package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.GoodIdentification;
import dev.yashverma.gatioms.domain.entity.GoodIdentificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodIdentificationRepository extends JpaRepository<GoodIdentification, GoodIdentificationId> {

    @Transactional(readOnly = true)
    @Query("SELECT g FROM GoodIdentification g JOIN FETCH g.product JOIN FETCH g.goodIdentificationType WHERE g.product.productId = :productId AND g.goodIdentificationType.goodIdentificationTypeId = :typeId")
    List<GoodIdentification> findByProductIdAndGoodIdentificationTypeId(@Param("productId") String productId, @Param("typeId") String typeId);

    @Transactional(readOnly = true)
    @Query("SELECT g FROM GoodIdentification g JOIN FETCH g.product JOIN FETCH g.goodIdentificationType WHERE g.idValue = :idValue AND g.goodIdentificationType.goodIdentificationTypeId = :typeId")
    Optional<GoodIdentification> findByIdValueAndGoodIdentificationTypeId(@Param("idValue") String idValue, @Param("typeId") String typeId);
}
