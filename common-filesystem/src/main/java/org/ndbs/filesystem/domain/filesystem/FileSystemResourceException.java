package org.ndbs.filesystem.domain.filesystem;

/**
 * FileSystemResourceException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public class FileSystemResourceException extends FileSystemException {
    public FileSystemResourceException(String message) {
        super(message);
    }

    public FileSystemResourceException(String message, Throwable previous) {
        super(message, previous);
    }
}
