package org.ndbs.file.domain;

/**
 * FilePathBuildingException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
public class FilePathBuildingException extends FileException {
    public FilePathBuildingException(String message) {
        super(message);
    }

    public FilePathBuildingException(String message, Throwable previous) {
        super(message, previous);
    }

    public FilePathBuildingException(Throwable previous) {
        super(previous);
    }
}
