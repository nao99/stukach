package org.ndbs.file.domain;

import org.ndbs.file.domain.model.File;

import java.util.UUID;

/**
 * GetFileService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
public interface GetFileService {
    /**
     * Gets a {@link File} by id
     *
     * @param id a file id
     *
     * @return a file
     * @throws FileNotFoundException if a file was not found by passed id
     */
    File getFile(UUID id) throws FileNotFoundException;
}
