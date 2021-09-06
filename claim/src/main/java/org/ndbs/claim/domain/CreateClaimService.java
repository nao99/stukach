package org.ndbs.claim.domain;

import org.ndbs.claim.api.CreateClaimCommand;
import org.ndbs.claim.domain.model.Claim;

/**
 * CreateClaimService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
public interface CreateClaimService {
    /**
     * Creates a {@link Claim} by passed parameters
     *
     * @param createClaimCommand a command with parameters
     * @return a claim
     */
    Claim create(CreateClaimCommand createClaimCommand);
}
