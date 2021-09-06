package org.ndbs.claim.api;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * GetClaimsCommand class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-05
 */
public class GetClaimsCommand {
    @NotNull(message = "User id should not be null")
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
