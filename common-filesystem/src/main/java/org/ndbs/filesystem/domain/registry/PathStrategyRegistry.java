package org.ndbs.filesystem.domain.registry;

import org.ndbs.filesystem.domain.PathStrategyRegistryException;
import org.ndbs.filesystem.domain.model.strategy.DefaultPathStrategy;
import org.ndbs.filesystem.domain.model.strategy.PathStrategy;

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

        var defaultPathStrategy = DefaultPathStrategy.create();
        addStrategy(defaultPathStrategy);
    }

    public static PathStrategyRegistry create() {
        return new PathStrategyRegistry();
    }

    public boolean hasStrategy(String name) {
        return strategies.containsKey(name);
    }

    public void addStrategy(PathStrategy strategy) {
        if (strategies.containsKey(strategy.getName())) {
            return;
        }

        strategies.put(strategy.getName(), strategy);
    }

    public PathStrategy getStrategy(String name) throws PathStrategyRegistryException {
        if (!hasStrategy(name)) {
            throw new PathStrategyRegistryException(String.format("Strategy \"%s\" is not registered", name));
        }

        return strategies.get(name);
    }
}
