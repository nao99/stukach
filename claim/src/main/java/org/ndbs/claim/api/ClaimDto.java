package org.ndbs.claim.api;

import org.ndbs.claim.domain.model.Claim;
import org.ndbs.claim.domain.model.ClaimStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ClaimDto class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-05
 */
public class ClaimDto {
    private final Claim claim;

    private ClaimDto(Claim claim) {
        this.claim = claim;
    }

    public static ClaimDto create(Claim claim) {
        return new ClaimDto(claim);
    }

    public UUID getId() {
        return claim.getId();
    }

    public UUID getUserId() {
        return claim.getUserId();
    }

    public ClaimStatus getStatus() {
        return claim.getStatus();
    }

    public LocalDateTime getCreatedAt() {
        return claim.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return claim.getUpdatedAt();
    }
}
