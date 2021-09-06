package org.ndbs.claim.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.claim.api.GetClaimsCommand;
import org.ndbs.claim.domain.model.Claim;
import org.ndbs.claim.persistent.api.ClaimRepository;
import org.ndbs.common.testing.DatabaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * GetClaimServiceImplTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-06
 */
@DisplayName("GetClaimServiceImpl test: Test for the Get Claim Service")
@Transactional
class GetClaimServiceImplTest extends DatabaseTest {
    @Autowired
    private ClaimRepository repository;

    private GetClaimServiceImpl getClaimService;

    @BeforeEach
    void setUp() throws Exception {
        getClaimService = new GetClaimServiceImpl(repository);
    }

    @DisplayName("Should get a claim by id")
    @Test
    void shouldGetClaimById() throws Exception {
        // given
        var userId = UUID.randomUUID();
        var claim = Claim.create(userId);

        var createdClaim = repository.save(claim);
        var createdClaimId = createdClaim.getId();

        // when
        var foundClaim = getClaimService.getClaim(createdClaimId);

        // then
        assertThat(foundClaim.getId())
            .isEqualTo(createdClaimId);
    }

    @DisplayName("Should throw a not found exception if claim was not found by id")
    @Test
    void shouldThrowNotFoundExceptionIfClaimWasNotFoundById() throws Exception {
        // when / then
        assertThrows(ClaimNotFoundException.class, () -> getClaimService.getClaim(UUID.randomUUID()));
    }

    @DisplayName("Should get a page of claims")
    @Test
    void shouldGetPageOfClaims() throws Exception {
        // given
        var userId = UUID.randomUUID();

        var claim1 = Claim.create(userId);
        var claim2 = Claim.create(userId);

        var getClaimsCommand = new GetClaimsCommand();
        getClaimsCommand.setUserId(userId);

        // when
        var foundClaimsPage1 = getClaimService.getClaims(getClaimsCommand, Pageable.ofSize(2));

        repository.save(claim1);
        repository.save(claim2);

        var foundClaimsPage2 = getClaimService.getClaims(getClaimsCommand, Pageable.ofSize(2));

        // then
        assertThat(foundClaimsPage1.getTotalElements())
            .isZero();

        assertThat(foundClaimsPage2.getTotalElements())
            .isEqualTo(2);
    }
}
