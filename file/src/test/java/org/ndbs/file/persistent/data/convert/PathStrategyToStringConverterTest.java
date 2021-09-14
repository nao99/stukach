package org.ndbs.file.persistent.data.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PathStrategyToStringConverterTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@DisplayName("PathStrategyToStringConverter test: Test for the Path Strategy To String Converter")
class PathStrategyToStringConverterTest {
    private PathStrategyToStringConverter converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new PathStrategyToStringConverter();
    }

    @DisplayName("Should return \"default\" string if path strategy is \"default\"")
    @Test
    void shouldReturnDefaultStringIfPathStrategyIsDefault() throws Exception {
        // given
        var defaultPathStrategy = DefaultPathStrategy.create();
        var expectedPathStrategyName = defaultPathStrategy.getName();

        // when
        var defaultPathStrategyString = converter.convert(defaultPathStrategy);

        // then
        assertThat(defaultPathStrategyString)
            .isEqualTo(expectedPathStrategyName);
    }

    @DisplayName("Should return null if path strategy is null")
    @Test
    void shouldReturnNullIfPathStrategyIsNull() throws Exception {
        // when
        var pathStrategyString = converter.convert(null);

        // then
        assertThat(pathStrategyString)
            .isNull();
    }
}
