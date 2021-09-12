package org.ndbs.filesystem.registry;

import org.ndbs.filesystem.domain.path.PathStrategyNotFoundException;
import org.ndbs.filesystem.domain.path.model.PathStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * PathStrategyRegistry class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public class PathStrategyRegistry {
    private final Map<String, PathStrategy> strategies;

    private PathStrategyRegistry() {
        this.strategies = new HashMap<>();
    }

    public static PathStrategyRegistry create() {
        return new PathStrategyRegistry();
    }

    public boolean hasStrategy(String name) {
        return strategies.containsKey(name);
    }

    public void addStrategy(PathStrategy strategy) {
        if (hasStrategy(strategy.getName())) {
            return;
        }

        strategies.put(strategy.getName(), strategy);
    }

    public PathStrategy getStrategy(String name) throws PathStrategyNotFoundException {
        if (!hasStrategy(name)) {
            throw new PathStrategyNotFoundException(String.format("Strategy \"%s\" is not registered", name));
        }

        return strategies.get(name);
    }
}
