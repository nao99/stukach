package org.ndbs.filesystem.domain.model;

import java.util.UUID;

/**
 * File interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
public interface File {
    /**
     * Gets a {@link File} id
     *
     * @return a file id
     */
    UUID getId();

    /**
     * Gets a {@link File} name
     *
     * @return a file name
     */
    String getName();
}
