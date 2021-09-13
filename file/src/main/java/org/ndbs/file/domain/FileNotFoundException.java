package org.ndbs.file.domain;

/**
 * FileNotFoundException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
public class FileNotFoundException extends FileException {
    public FileNotFoundException(String message) {
        super(message);
    }
}
