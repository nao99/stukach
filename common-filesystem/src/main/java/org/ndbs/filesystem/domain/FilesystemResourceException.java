package org.ndbs.filesystem.domain;

/**
 * FilesystemResourceException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public class FilesystemResourceException extends FilesystemException {
    public FilesystemResourceException(String message) {
        super(message);
    }

    public FilesystemResourceException(String message, Throwable previous) {
        super(message, previous);
    }
}
