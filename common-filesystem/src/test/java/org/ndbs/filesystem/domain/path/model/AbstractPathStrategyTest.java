package org.ndbs.filesystem.domain.path.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.filesystem.model.AwesomeFile;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AbstractPathStrategyTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
@DisplayName("AbstractPathStrategy test: Test for the Abstract Path Strategy")
class AbstractPathStrategyTest {
    @DisplayName("Should get the same name with which was created")
    @Test
    void shouldGetSameNameWithWhichWasCreated() throws Exception {
        // given
        var expectedName = "awesome_path_strategy";
        var strategy = AwesomePathStrategy.create(expectedName);

        // when
        var strategyName = strategy.getName();

        // then
        assertThat(strategyName)
            .isEqualTo(expectedName);
    }

    @DisplayName("Should build a correct path to a file")
    @Test
    void shouldBuildCorrectPathToFile() throws Exception {
        // given
        var fileId = UUID.randomUUID();
        var file = AwesomeFile.create(fileId, "awesome_file.mp4");

        var strategy = AwesomePathStrategy.create("awesome_path_strategy");

        // when
        var filePath = strategy.buildPath(file);

        // then
        assertThat(filePath)
            .isEqualTo("awesome/path/strategy/awesome_file.mp4");
    }
}
