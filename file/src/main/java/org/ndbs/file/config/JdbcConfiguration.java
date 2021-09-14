package org.ndbs.file.config;

import org.ndbs.file.persistent.data.convert.FileSystemIdToStringConverter;
import org.ndbs.file.persistent.data.convert.PathStrategyToStringConverter;
import org.ndbs.file.persistent.data.convert.StringToFileSystemIdConverter;
import org.ndbs.file.persistent.data.convert.StringToPathStrategyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * JdbcConfiguration class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {
    private final StringToFileSystemIdConverter stringToFileSystemIdConverter;
    private final FileSystemIdToStringConverter fileSystemIdToStringConverter;
    private final StringToPathStrategyConverter stringToPathStrategyConverter;
    private final PathStrategyToStringConverter pathStrategyToStringConverter;

    @Autowired
    public JdbcConfiguration(
        StringToFileSystemIdConverter stringToFileSystemIdConverter,
        FileSystemIdToStringConverter fileSystemIdToStringConverter,
        PathStrategyToStringConverter pathStrategyToStringConverter,
        StringToPathStrategyConverter stringToPathStrategyConverter
    ) {
        this.stringToFileSystemIdConverter = stringToFileSystemIdConverter;
        this.fileSystemIdToStringConverter = fileSystemIdToStringConverter;
        this.stringToPathStrategyConverter = stringToPathStrategyConverter;
        this.pathStrategyToStringConverter = pathStrategyToStringConverter;
    }

    @Override
    @NonNull
    public JdbcCustomConversions jdbcCustomConversions() {
        var customConverters = List.of(
            stringToFileSystemIdConverter,
            fileSystemIdToStringConverter,
            stringToPathStrategyConverter,
            pathStrategyToStringConverter
        );

        return new JdbcCustomConversions(customConverters);
    }
}
