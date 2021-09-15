package org.ndbs.filesystem.domain.filesystem;

/**
 * FileSystemResourceExistsException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-15
 */
public class FileSystemResourceExistsException extends FileSystemException {
    public FileSystemResourceExistsException(String message) {
        super(message);
    }

    public FileSystemResourceExistsException(String message, Throwable previous) {
        super(message, previous);
    }

    public FileSystemResourceExistsException(Throwable previous) {
        super(previous);
    }
}
