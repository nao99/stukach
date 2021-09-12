package org.ndbs.filesystem.domain.filesystem;

/**
 * FileSystemNotFoundException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
public class FileSystemNotFoundException extends FileSystemException {
    public FileSystemNotFoundException(String message) {
        super(message);
    }

    public FileSystemNotFoundException(String message, Throwable previous) {
        super(message, previous);
    }

    public FileSystemNotFoundException(Throwable previous) {
        super(previous);
    }
}
