package org.ndbs.file.domain;

/**
 * FileException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
public class FileException extends RuntimeException {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable previous) {
        super(message, previous);
    }

    public FileException(Throwable previous) {
        super(previous);
    }
}
