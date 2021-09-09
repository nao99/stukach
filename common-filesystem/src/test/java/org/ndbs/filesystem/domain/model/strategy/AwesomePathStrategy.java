package org.ndbs.filesystem.domain.model.strategy;

import org.ndbs.filesystem.domain.PathStrategyException;
import org.ndbs.filesystem.domain.model.File;

/**
 * AwesomePathStrategy class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
public class AwesomePathStrategy extends AbstractPathStrategy {
    private AwesomePathStrategy(String name) {
        super(name);
    }

    public static AwesomePathStrategy create(String name) {
        return new AwesomePathStrategy(name);
    }

    @Override
    protected String buildDirectoryPath(File file) throws PathStrategyException {
        return "awesome/path/strategy/";
    }
}
