package org.ndbs.filesystem.domain.filesystem;

/**
 * FileSystemException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public class FileSystemException extends RuntimeException {
    public FileSystemException(String message) {
        super(message);
    }

    public FileSystemException(String message, Throwable previous) {
        super(message, previous);
    }

    public FileSystemException(Throwable previous) {
        super(previous);
    }
}
