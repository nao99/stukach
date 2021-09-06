package org.ndbs.claim.persistent.api;

import org.ndbs.claim.domain.model.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * ClaimRepository interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
public interface ClaimRepository extends CrudRepository<Claim, UUID> {
    /**
     * Finds a {@link Page<Claim>} by user id
     *
     * @param userId   a user id
     * @param pageable a pageable
     *
     * @return a page of claims
     */
    Page<Claim> findByUserId(UUID userId, Pageable pageable);
}
