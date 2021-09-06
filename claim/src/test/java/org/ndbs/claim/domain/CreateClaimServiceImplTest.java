package org.ndbs.claim.domain;

import org.ndbs.claim.api.CreateClaimCommand;
import org.ndbs.claim.domain.model.ClaimStatus;
import org.ndbs.common.testing.DatabaseTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.claim.persistent.api.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * CreateClaimServiceImplTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-06
 */
@DisplayName("CreateClaimServiceImpl test: Test for the Create Claim Service")
@Transactional
class CreateClaimServiceImplTest extends DatabaseTest {
    @Autowired
    private ClaimRepository repository;

    private CreateClaimServiceImpl createClaimService;

    @BeforeEach
    void setUp() throws Exception {
        createClaimService = new CreateClaimServiceImpl(repository);
    }

    @DisplayName("Should create a claim with status 'CREATED' and return it")
    @Test
    void shouldCreateClaimWithCreatedStatusAndReturnIt() throws Exception {
        // given
        var userId = UUID.randomUUID();

        var createClaimCommand = new CreateClaimCommand();
        createClaimCommand.setUserId(userId);

        // when
        var claim = createClaimService.create(createClaimCommand);
        var claimId = claim.getId();

        assertThat(claimId)
            .isNotNull();

        var foundClaimOptional = repository.findById(claimId);

        // then
        assertThat(foundClaimOptional)
            .isPresent();

        var foundClaim = foundClaimOptional.get();

        assertThat(foundClaim.getUserId())
            .isEqualTo(userId);

        assertThat(foundClaim.getStatus())
            .isEqualTo(ClaimStatus.CREATED);
    }
}
