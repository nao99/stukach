package org.ndbs.claim.domain;

import org.ndbs.claim.api.CreateClaimCommand;
import org.ndbs.claim.domain.model.Claim;
import org.ndbs.claim.persistent.api.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CreateClaimServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
@Service
public class CreateClaimServiceImpl implements CreateClaimService {
    private final ClaimRepository repository;

    @Autowired
    public CreateClaimServiceImpl(ClaimRepository repository) {
        this.repository = repository;
    }

    @Override
    public Claim create(CreateClaimCommand createClaimCommand) {
        var claim = Claim.create(createClaimCommand.getUserId());
        return repository.save(claim);
    }
}
