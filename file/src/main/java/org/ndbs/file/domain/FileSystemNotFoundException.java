package org.ndbs.file.domain;

/**
 * FileFileSystemNotFoundException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
public class FileSystemNotFoundException extends FileException {
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
