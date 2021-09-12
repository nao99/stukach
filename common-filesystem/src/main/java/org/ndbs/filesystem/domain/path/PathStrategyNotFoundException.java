package org.ndbs.filesystem.domain.path;

/**
 * PathStrategyNotFoundException class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public class PathStrategyNotFoundException extends PathStrategyException {
    public PathStrategyNotFoundException(String message) {
        super(message);
    }
}
