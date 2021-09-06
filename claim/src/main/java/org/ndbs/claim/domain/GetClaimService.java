package org.ndbs.claim.domain;

import org.ndbs.claim.api.GetClaimsCommand;
import org.ndbs.claim.domain.model.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * GetClaimService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
public interface GetClaimService {
    /**
     * Gets a {@link Claim} by id
     *
     * @param id an id
     *
     * @return a claim
     * @throws ClaimNotFoundException if a claim by passed id was not found
     */
    Claim getClaim(UUID id) throws ClaimNotFoundException;

    /**
     * Gets a {@link Claim}s list by specified parameters
     *
     * @param getClaimsCommand a command with parameters
     * @param pageable         a pageable
     *
     * @return a claims list
     */
    Page<Claim> getClaims(GetClaimsCommand getClaimsCommand, Pageable pageable);
}
