package org.ndbs.claim.domain;

/**
 * ClaimNotFoundException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
public class ClaimNotFoundException extends ClaimException {
    public ClaimNotFoundException(String message) {
        super(message);
    }
}
