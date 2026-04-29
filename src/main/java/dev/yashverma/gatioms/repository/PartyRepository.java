package dev.yashverma.gatioms.repository;

import dev.yashverma.gatioms.domain.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, String> {
}
