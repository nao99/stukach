package org.ndbs.claim.controller;

import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.claim.api.CreateClaimCommand;
import org.ndbs.claim.api.GetClaimsCommand;
import org.ndbs.claim.domain.ClaimNotFoundException;
import org.ndbs.claim.domain.CreateClaimService;
import org.ndbs.claim.domain.GetClaimService;
import org.ndbs.claim.domain.model.Claim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ClaimControllerTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-06
 */
@DisplayName("ClaimController test: Test for the REST Claim Controller")
@WebMvcTest(ClaimController.class)
class ClaimControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${app.rest.api.prefix}")
    private String apiPrefix;

    @Value("${app.rest.api.latest-version}")
    private String apiVersion;

    @MockBean
    private GetClaimService getClaimService;

    @MockBean
    private CreateClaimService createClaimService;

    @DisplayName("Should return a claim and 200 status if the claim exists")
    @Test
    void shouldReturnClaimAnd200StatusIfClaimExists() throws Exception {
        // given
        var apiUrl = String.format("/%s/%s/claims/{claimId}", apiPrefix, apiVersion);

        var claimId = UUID.randomUUID();
        var userId = UUID.randomUUID();

        var expectedClaim = Claim.create(userId);

        // when
        given(getClaimService.getClaim(claimId))
            .willReturn(expectedClaim);

        // then
        mockMvc.perform(get(apiUrl, claimId).accept("application/json; charset=utf-8"))
            .andExpect(status().isOk());
    }

    @DisplayName("Should return 400 status code if claim id is invalid")
    @Test
    void shouldReturn400StatusCodeIfClaimIdIsInvalid() throws Exception {
        // given
        var apiUrl = String.format("/%s/%s/claims/{claimId}", apiPrefix, apiVersion);
        var claimId = "invalid_claim_id";

        // when / then
        mockMvc.perform(get(apiUrl, claimId).accept("application/json; charset=utf-8"))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("Should return 404 status if a claim does not exist")
    @Test
    void shouldReturn404StatusIfClaimDoesNotExist() throws Exception {
        // given
        var apiUrl = String.format("/%s/%s/claims/{claimId}", apiPrefix, apiVersion);
        var claimId = UUID.randomUUID();

        // when
        given(getClaimService.getClaim(claimId))
            .willThrow(new ClaimNotFoundException("Claim with this id was not found"));

        // then
        mockMvc.perform(get(apiUrl, claimId).accept("application/json; charset=utf-8"))
            .andExpect(status().isNotFound());
    }

    @DisplayName("Should return a list of claims and 200 status for a specified user without any other parameters")
    @Test
    void shouldReturnListOfClaimsAnd200StatusForUserWithoutAnotherParameters() throws Exception {
        // given
        var userId = UUID.randomUUID();

        var expectedGetParameters = String.format("?userId=%s", userId);
        var apiUrl = String.format("/%s/%s/claims%s", apiPrefix, apiVersion, expectedGetParameters);

        var expectedClaims = List.of(Claim.create(userId));
        var expectedContentPart = String.format("{\"content\":[{\"id\":null,\"userId\":\"%s\",", userId);

        // when
        given(getClaimService.getClaims(any(GetClaimsCommand.class), any(Pageable.class)))
            .willReturn(new PageImpl<>(expectedClaims));

        // then
        mockMvc.perform(get(apiUrl).accept("application/json; charset=utf-8"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expectedContentPart)));
    }

    @DisplayName("Should return 400 status if some parameters are invalid")
    @Test
    void shouldReturn400StatusIfSomeParametersInvalid() throws Exception {
        // given
        var userId = "invalid_user_id";

        var expectedGetParameters = String.format("?userId=%s", userId);
        var apiUrl = String.format("/%s/%s/claims%s", apiPrefix, apiVersion, expectedGetParameters);

        // when / then
        mockMvc.perform(get(apiUrl).accept("application/json; charset=utf-8"))
            .andExpect(status().isBadRequest());

        verify(getClaimService, never())
            .getClaims(any(GetClaimsCommand.class), any(Pageable.class));
    }

    @DisplayName("Should not get claims and should return 400 status if user id was not specified")
    @Test
    void shouldNotGetClaimsAndShouldReturn400StatusIfUserIdWasNotSpecified() throws Exception {
        // given
        var apiUrl = String.format("/%s/%s/claims", apiPrefix, apiVersion);

        // when / then
        mockMvc.perform(get(apiUrl).accept("application/json; charset=utf-8"))
            .andExpect(status().isBadRequest());

        verify(getClaimService, never())
            .getClaims(any(GetClaimsCommand.class), any(Pageable.class));
    }

    @DisplayName("Should create and return a new claim and 201 status")
    @Test
    void shouldCreateAndReturnNewClaimAnd201Status() throws Exception {
        // given
        var userId = UUID.randomUUID();
        var apiUrl = String.format("/%s/%s/claims", apiPrefix, apiVersion);

        var expectedClaim = Claim.create(userId);

        var createClaimCommand = new CreateClaimCommand();
        createClaimCommand.setUserId(userId);

        var gson = new GsonBuilder().create();
        var createClaimCommandJson = gson.toJson(createClaimCommand);

        // when
        given(createClaimService.create(any(CreateClaimCommand.class)))
            .willReturn(expectedClaim);

        // then
        mockMvc.perform(
            post(apiUrl)
                .accept("application/json; charset=utf-8")
                .contentType("application/json")
                .content(createClaimCommandJson)
        )
            .andExpect(status().isCreated());
    }

    @DisplayName("Should not create a claim and should return 400 status if user id was not specified")
    @Test
    void shouldNotCreateClaimAndShouldReturn400StatusIfUserIdWasNotSpecified() throws Exception {
        // given
        var userId = UUID.randomUUID();
        var apiUrl = String.format("/%s/%s/claims", apiPrefix, apiVersion);

        var createClaimCommand = new CreateClaimCommand();

        var gson = new GsonBuilder().create();
        var createClaimCommandJson = gson.toJson(createClaimCommand);

        // when / then
        mockMvc.perform(
            post(apiUrl)
                .accept("application/json; charset=utf-8")
                .contentType("application/json")
                .content(createClaimCommandJson)
        )
            .andExpect(status().isBadRequest());

        verify(createClaimService, never())
            .create(any(CreateClaimCommand.class));
    }

    @DisplayName("Should not create a claim and should return 400 status if user id is invalid")
    @Test
    void shouldNotCreateClaimAndShouldReturn400StatusIfUserIdIsInvalid() throws Exception {
        // given
        var apiUrl = String.format("/%s/%s/claims", apiPrefix, apiVersion);
        var createClaimCommandJson = "{\"userId\": \"invalid_user_id\"}";

        // when / then
        mockMvc.perform(
            post(apiUrl)
                .accept("application/json; charset=utf-8")
                .contentType("application/json")
                .content(createClaimCommandJson)
        )
            .andExpect(status().isBadRequest());

        verify(createClaimService, never())
            .create(any(CreateClaimCommand.class));
    }
}
