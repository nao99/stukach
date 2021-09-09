package org.ndbs.filesystem.domain;

/**
 * FilesystemException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public class FilesystemException extends RuntimeException {
    public FilesystemException(String message) {
        super(message);
    }

    public FilesystemException(String message, Throwable previous) {
        super(message, previous);
    }

    public FilesystemException(Throwable previous) {
        super(previous);
    }
}
