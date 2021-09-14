package org.ndbs.filesystem.domain.filesystem;

import java.nio.file.Path;

/**
 * FileSystemService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-08-04
 */
public interface FileSystemService {
    /**
     * Copies a {@link Path} from one place to another
     * Available only for files (not directories)
     *
     * @param source      a source resource
     * @param destination a destination resource
     *
     * @throws FileSystemException if something was wrong
     */
    void copy(Path source, Path destination) throws FileSystemException;

    /**
     * Moves a {@link Path} from one place to another
     * Available only for files (not directories)
     *
     * @param source      a source resource
     * @param destination a destination resource
     *
     * @throws FileSystemException if something was wrong
     */
    void move(Path source, Path destination) throws FileSystemException;

    /**
     * Deletes a {@link Path}
     * Available for files and directories
     *
     * @param source a source resource
     * @throws FileSystemException if something was wrong
     */
    void delete(Path source) throws FileSystemException;
}
