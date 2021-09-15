package org.ndbs.filesystem.domain.filesystem;

/**
 * FileSystemResourceParentPathsAreDifferentException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-15
 */
public class FileSystemResourceParentPathsAreDifferentException extends FileSystemException {
    public FileSystemResourceParentPathsAreDifferentException(String message) {
        super(message);
    }

    public FileSystemResourceParentPathsAreDifferentException(String message, Throwable previous) {
        super(message, previous);
    }

    public FileSystemResourceParentPathsAreDifferentException(Throwable previous) {
        super(previous);
    }
}
