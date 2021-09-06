package org.ndbs.claim.domain.model;

/**
 * ClaimStatus enum
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
public enum ClaimStatus {
    CREATED,
    PROCESSING,
    FAILED,
    RESOLVED;
}
