package org.ndbs.filesystem.domain.path;

import org.ndbs.filesystem.domain.filesystem.FileSystemException;

/**
 * PathStrategyException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public class PathStrategyException extends FileSystemException {
    public PathStrategyException(String message) {
        super(message);
    }
}
