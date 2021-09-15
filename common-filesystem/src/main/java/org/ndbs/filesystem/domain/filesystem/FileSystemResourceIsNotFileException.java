package org.ndbs.filesystem.domain.filesystem;

/**
 * FileSystemResourceIsNotFileException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-15
 */
public class FileSystemResourceIsNotFileException extends FileSystemException {
    public FileSystemResourceIsNotFileException(String message) {
        super(message);
    }

    public FileSystemResourceIsNotFileException(String message, Throwable previous) {
        super(message, previous);
    }

    public FileSystemResourceIsNotFileException(Throwable previous) {
        super(previous);
    }
}
