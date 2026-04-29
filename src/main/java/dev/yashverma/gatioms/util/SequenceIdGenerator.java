// File: src/main/java/dev/yashverma/gatioms/util/SequenceIdGenerator.java
// OFBiz equivalent: delegator.getNextSeqId("EntityName")

package dev.yashverma.gatioms.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility to generate sequential IDs.
 * Mirrors the OFBiz/Moqui sequence generation logic.
 */
@Component
@Slf4j
public class SequenceIdGenerator {
    private final AtomicLong counter = new AtomicLong(10000L);
    
    public String nextId() {
        return String.valueOf(counter.getAndIncrement());
    }
    
    /**
     * Generates an ID with a specific prefix.
     * Example: nextId("ORD") -> "ORD10000"
     */
    public String nextId(String prefix) {
        return prefix + counter.getAndIncrement();
    }
}
