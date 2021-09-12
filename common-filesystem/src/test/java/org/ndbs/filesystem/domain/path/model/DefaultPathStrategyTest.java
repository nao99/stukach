package org.ndbs.filesystem.domain.path.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.path.PathStrategyException;
import org.ndbs.filesystem.domain.filesystem.model.AwesomeFile;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * DefaultPathStrategyTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
@DisplayName("DefaultPathStrategy test: Test for the Default Path Strategy class")
class DefaultPathStrategyTest {
    @DisplayName("Should build a correct file path")
    @Test
    void shouldBuildCorrectFilePath() throws Exception {
        // given
        var fileIdString = "3655b36a-1148-11ec-8a0b-f35470d8bc24";
        var fileIdUuid = UUID.fromString(fileIdString);

        var file = AwesomeFile.create(fileIdUuid, "awesome_file.mp4");
        var strategy = DefaultPathStrategy.create();

        // when
        var filePath = strategy.buildDirectoryPath(file);

        // then
        assertThat(filePath)
            .isEqualTo("/3655b36a/1148/11ec/8a0b/f35470d8bc24/");
    }

    @DisplayName("Should throw an exception when file id is null")
    @Test
    void shouldThrowExceptionWhenFileIdIsNull() throws Exception {
        // given
        var file = AwesomeFile.create(null, "awesome_file.mp4");
        var strategy = DefaultPathStrategy.create();

        // when / then
        assertThrows(PathStrategyException.class, () -> strategy.buildPath(file));
    }
}
