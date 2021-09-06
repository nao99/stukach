package org.ndbs.claim.controller;

import org.ndbs.claim.api.ClaimDto;
import org.ndbs.claim.api.CreateClaimCommand;
import org.ndbs.claim.api.GetClaimsCommand;
import org.ndbs.claim.domain.ClaimNotFoundException;
import org.ndbs.claim.domain.CreateClaimService;
import org.ndbs.claim.domain.GetClaimService;
import org.ndbs.claim.domain.model.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * ClaimController class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-05
 */
@RestController
@RequestMapping("/${app.rest.api.prefix}/${app.rest.api.latest-version}/claims")
public class ClaimController {
    private final GetClaimService getClaimService;
    private final CreateClaimService createClaimService;

    @Autowired
    public ClaimController(GetClaimService getClaimService, CreateClaimService createClaimService) {
        this.getClaimService = getClaimService;
        this.createClaimService = createClaimService;
    }

    /**
     * API endpoint to get a {@link Claim} by id
     *
     * @param claimId a claim id
     *
     * @return a claim
     * @throws ResponseStatusException with status 404 if a claim was not found by passed id
     */
    @GetMapping("/{claimId}")
    public ResponseEntity<ClaimDto> getClaim(@PathVariable("claimId") UUID claimId) throws ResponseStatusException {
        try {
            var claim = getClaimService.getClaim(claimId);
            var claimDto = ClaimDto.create(claim);

            return ResponseEntity.ok(claimDto);
        } catch (ClaimNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    /**
     * API endpoint to get {@link Claim}s list
     *
     * @param getClaimsCommand a get claims command with parameters
     * @param pageable         a pageable
     *
     * @return a claims list
     */
    @GetMapping
    public ResponseEntity<Page<Claim>> getClaims(@Validated GetClaimsCommand getClaimsCommand, Pageable pageable) {
        var claimPage = getClaimService.getClaims(getClaimsCommand, pageable);
        return ResponseEntity.ok(claimPage);
    }

    /**
     * API endpoint to create a {@link Claim}
     *
     * @param createClaimCommand a create claim command with parameters
     * @return a newly created claim
     */
    @PostMapping
    public ResponseEntity<ClaimDto> createClaim(@Validated @RequestBody CreateClaimCommand createClaimCommand) {
        var claim = createClaimService.create(createClaimCommand);
        var claimDto = ClaimDto.create(claim);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(claimDto);
    }
}
