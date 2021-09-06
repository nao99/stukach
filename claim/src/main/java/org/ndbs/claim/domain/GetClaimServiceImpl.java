package org.ndbs.claim.domain;

import org.ndbs.claim.api.GetClaimsCommand;
import org.ndbs.claim.domain.model.Claim;
import org.ndbs.claim.persistent.api.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * GetClaimServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
@Service
public class GetClaimServiceImpl implements GetClaimService {
    private final ClaimRepository repository;

    @Autowired
    public GetClaimServiceImpl(ClaimRepository repository) {
        this.repository = repository;
    }

    @Override
    public Claim getClaim(UUID id) throws ClaimNotFoundException {
        return repository.findById(id)
            .orElseThrow(() -> new ClaimNotFoundException(String.format("Claim with \"%s\" id was not found", id)));
    }

    @Override
    public Page<Claim> getClaims(GetClaimsCommand getClaimsCommand, Pageable pageable) {
        return repository.findByUserId(getClaimsCommand.getUserId(), pageable);
    }
}
