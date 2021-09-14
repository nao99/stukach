package org.ndbs.file.config;

import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;
import org.ndbs.filesystem.domain.path.model.PathStrategy;
import org.ndbs.filesystem.registry.PathStrategyRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PathStrategyConfiguration class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-10
 */
@Configuration
public class PathStrategyConfiguration {
    @Bean
    public PathStrategy defaultPathStrategy() {
        return DefaultPathStrategy.create();
    }

    @Bean
    public PathStrategyRegistry pathStrategyRegistry() {
        var pathStrategyRegistry = PathStrategyRegistry.create();
        fillPathStrategyRegistry(pathStrategyRegistry);

        return pathStrategyRegistry;
    }

    private void fillPathStrategyRegistry(PathStrategyRegistry registry) {
        var defaultPathStrategy = defaultPathStrategy();
        registry.addStrategy(defaultPathStrategy);
    }
}
