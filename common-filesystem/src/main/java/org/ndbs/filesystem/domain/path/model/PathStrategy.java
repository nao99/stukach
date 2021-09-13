package org.ndbs.filesystem.domain.path.model;

import org.ndbs.filesystem.domain.path.PathStrategyException;
import org.ndbs.filesystem.domain.filesystem.model.File;

/**
 * PathStrategy interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public interface PathStrategy {
    /**
     * Gets a name of strategy
     *
     * @return a name of strategy
     */
    String getName();

    /**
     * Builds a path to file storage
     *
     * @param file a file
     *
     * @return a file path
     * @throws PathStrategyException if a path cannot be built
     */
    String buildPath(File file) throws PathStrategyException;
}
