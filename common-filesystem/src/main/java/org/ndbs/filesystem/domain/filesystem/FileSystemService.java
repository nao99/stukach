package org.ndbs.filesystem.domain.filesystem;

import org.ndbs.filesystem.domain.filesystem.model.FileSystemResource;

/**
 * FileSystemService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-08-04
 */
public interface FileSystemService {
    /**
     * Copies a {@link FileSystemResource} from one place to another
     * Available only for files (not directories)
     *
     * @param source      a source resource
     * @param destination a destination resource
     *
     * @throws FileSystemException if something was wrong
     */
    void copy(FileSystemResource source, FileSystemResource destination) throws FileSystemException;

    /**
     * Moves a {@link FileSystemResource} from one place to another
     * Available only for files (not directories)
     *
     * @param source      a source resource
     * @param destination a destination resource
     *
     * @throws FileSystemException if something was wrong
     */
    void move(FileSystemResource source, FileSystemResource destination) throws FileSystemException;

    /**
     * Deletes a {@link FileSystemResource}
     * Available for files and directories
     *
     * @param source a source resource
     * @throws FileSystemException if something was wrong
     */
    void delete(FileSystemResource source) throws FileSystemException;
}
