package org.ndbs.filesystem.domain.path.model;

import org.ndbs.filesystem.domain.path.PathStrategyException;
import org.ndbs.filesystem.domain.filesystem.model.File;

/**
 * AbstractPathStrategy class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
abstract class AbstractPathStrategy implements PathStrategy {
    private final String name;

    AbstractPathStrategy(String name) {
        this.name = name;
    }

    abstract String buildDirectoryPath(File file) throws PathStrategyException;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String buildPath(File file) throws PathStrategyException {
        var directoryPath = buildDirectoryPath(file);
        directoryPath = directoryPath.replaceAll("/$", "");

        return String.format("%s/%s", directoryPath, file.getName());
    }
}
