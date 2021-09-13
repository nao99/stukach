package org.ndbs.filesystem.domain.filesystem;

/**
 * FileSystemIOException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-13
 */
public class FileSystemIOException extends FileSystemException {
    public FileSystemIOException(String message) {
        super(message);
    }

    public FileSystemIOException(String message, Throwable previous) {
        super(message, previous);
    }

    public FileSystemIOException(Throwable previous) {
        super(previous);
    }
}
