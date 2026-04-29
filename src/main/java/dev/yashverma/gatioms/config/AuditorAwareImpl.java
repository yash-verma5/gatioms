// File: src/main/java/dev/yashverma/gatioms/config/AuditorAwareImpl.java

package dev.yashverma.gatioms.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of AuditorAware to provide the current user for JPA Auditing.
 */
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Returns "SYSTEM" for now — Sprint 5 will wire in real user login context
        return Optional.of("SYSTEM");
    }
}
