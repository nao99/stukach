package org.ndbs.claim.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Claim class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
@Table("claims")
public class Claim {
    @Id
    private final UUID id;
    private final UUID userId;
    private ClaimStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Claim(UUID id, UUID userId, ClaimStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Claim create(UUID userId) {
        var currentDateTime = LocalDateTime.now();
        return new Claim(null, userId, ClaimStatus.CREATED, currentDateTime, currentDateTime);
    }

    Claim withId(UUID id) {
        return new Claim(id, userId, status, createdAt, updatedAt);
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void changeStatus(ClaimStatus status) {
        this.status = status;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void updateTimestamp() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Claim{" +
            "id=" + id +
            ", userId=" + userId +
            ", status=" + status +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
