package org.ndbs.claim.domain;

/**
 * ClaimException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-04
 */
public class ClaimException extends RuntimeException {
    public ClaimException(String message) {
        super(message);
    }
}
