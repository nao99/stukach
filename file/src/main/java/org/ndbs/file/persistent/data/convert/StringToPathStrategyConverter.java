package org.ndbs.file.persistent.data.convert;

import org.ndbs.filesystem.domain.path.PathStrategyNotFoundException;
import org.ndbs.filesystem.domain.path.model.PathStrategy;
import org.ndbs.filesystem.registry.PathStrategyRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * StringToPathStrategyConverter enum
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Component
@ReadingConverter
public class StringToPathStrategyConverter implements Converter<String, PathStrategy> {
    private final PathStrategyRegistry pathStrategyRegistry;

    @Autowired
    public StringToPathStrategyConverter(PathStrategyRegistry pathStrategyRegistry) {
        this.pathStrategyRegistry = pathStrategyRegistry;
    }

    @Override
    @Nullable
    public PathStrategy convert(@Nullable String pathStrategyString) throws PathStrategyNotFoundException {
        return pathStrategyString == null
            ? null
            : pathStrategyRegistry.getStrategy(pathStrategyString);
    }
}
