package org.ndbs.filesystem.domain.filesystem;

/**
 * FileSystemResourceNotFoundException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-15
 */
public class FileSystemResourceNotFoundException extends FileSystemException {
    public FileSystemResourceNotFoundException(String message) {
        super(message);
    }

    public FileSystemResourceNotFoundException(String message, Throwable previous) {
        super(message, previous);
    }

    public FileSystemResourceNotFoundException(Throwable previous) {
        super(previous);
    }
}
