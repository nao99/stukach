package org.ndbs.file.persistent.data.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.path.PathStrategyNotFoundException;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;
import org.ndbs.filesystem.registry.PathStrategyRegistry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * StringToPathStrategyConverterTest class
 *
 * @author Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since 2021-09-14
 */
@DisplayName("StringToPathStrategyConverterTest")
class StringToPathStrategyConverterTest {
    private PathStrategyRegistry pathStrategyRegistry;
    private StringToPathStrategyConverter stringToPathStrategyConverter;

    @BeforeEach
    void setUp() throws Exception {
        pathStrategyRegistry = PathStrategyRegistry.create();
        stringToPathStrategyConverter = new StringToPathStrategyConverter(pathStrategyRegistry);
    }

    @DisplayName("Should return \"default\" path strategy object if strategy is \"default\" string")
    @Test
    void shouldReturnDefaultPathStrategyObjectIfStrategyIsDefaultString() throws Exception {
        // given
        var expectedDefaultStrategy = DefaultPathStrategy.create();
        var defaultStrategyName = expectedDefaultStrategy.getName();

        pathStrategyRegistry.addStrategy(expectedDefaultStrategy);

        // when
        var defaultPathStrategy = stringToPathStrategyConverter.convert(defaultStrategyName);

        // then
        assertThat(defaultPathStrategy)
            .isSameAs(expectedDefaultStrategy);
    }

    @DisplayName("Should return null if strategy is null")
    @Test
    void shouldReturnNullIfStrategyIsNull() throws Exception {
        // when
        var defaultPathStrategy = stringToPathStrategyConverter.convert(null);

        // then
        assertThat(defaultPathStrategy)
            .isNull();
    }

    @DisplayName("Should throw an exception if strategy is not null and does not exist in registry")
    @Test
    void shouldThrowExceptionIfStrategyIsNotNullAndDoesNotExistInRegistry() throws Exception {
        // when / then
        assertThrows(
            PathStrategyNotFoundException.class,
            () -> stringToPathStrategyConverter.convert("unknown_path_strategy")
        );
    }
}
