srpackage dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.OrderFulfillmentGroup;
import dev.yashverma.gatioms.domain.entity.OrderFulfillmentGroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderFulfillmentGroupRepository extends JpaRepository<OrderFulfillmentGroup, OrderFulfillmentGroupId> {
}
