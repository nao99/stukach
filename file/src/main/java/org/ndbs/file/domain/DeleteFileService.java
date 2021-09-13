package org.ndbs.file.domain;

import org.ndbs.file.domain.model.File;

/**
 * DeleteFileService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
public interface DeleteFileService {
    /**
     * Deletes a {@link File}
     *
     * @param file a file to delete
     *
     * @throws FileException if something was wrong
     */
    void delete(File file) throws FileException;
}
