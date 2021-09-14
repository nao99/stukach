package org.ndbs.file.persistent.data.convert;

import org.ndbs.filesystem.domain.path.model.PathStrategy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * PathStrategyToStringConverter class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Component
@WritingConverter
public class PathStrategyToStringConverter implements Converter<PathStrategy, String> {
    @Override
    @Nullable
    public String convert(@Nullable PathStrategy pathStrategy) {
        return pathStrategy == null
            ? null
            : pathStrategy.getName();
    }
}
