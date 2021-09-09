package org.ndbs.filesystem.domain.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FilesystemResourceTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
@DisplayName("FilesystemResource test: Test for the Filesystem Resource class")
class FilesystemResourceTest {
    private File tempFile;

    @BeforeEach
    void setUp() throws Exception {
        this.tempFile = File.createTempFile("awesome_file", ".mp4");
    }

    @AfterEach
    void tearDown() throws Exception {
        this.tempFile.deleteOnExit();
    }

    @DisplayName("Should get a zero size of temp file")
    @Test
    void shouldGetZeroSizeOfTempFile() throws Exception {
        // given
        var resource = FilesystemResource.asRoot(tempFile.getPath());

        // when
        var filesize = resource.getFilesize();

        // then
        assertThat(filesize)
            .isZero();
    }

    @DisplayName("Should get \"video/mp4\" mime type of temp file")
    @Test
    void shouldGetVideoMp4MimeTypeOfTempFile() throws Exception {
        // given
        var resource = FilesystemResource.asRoot(tempFile.getPath());

        // when
        var mimeType = resource.getMimeType();

        // then
        assertThat(mimeType)
            .isEqualTo("video/mp4");
    }

    @DisplayName("Should get a name of temp file")
    @Test
    void shouldGetNameOfTempFile() throws Exception {
        // given
        var resource = FilesystemResource.asRoot(tempFile.getPath());

        // when
        var name = resource.getName();

        // then
        assertThat(name)
            .isEqualTo(tempFile.getName());
    }

    @DisplayName("Should get \"mp4\" extension of temp file")
    @Test
    void shouldGetMp4ExtensionOfTempFile() throws Exception {
        // given
        var resource = FilesystemResource.asRoot(tempFile.getPath());

        // when
        var extension = resource.getExtension();

        // then
        assertThat(extension)
            .isEqualTo("mp4");
    }

    @DisplayName("Should get an absolute path of temp file")
    @Test
    void shouldGetAbsolutePathOfTempFile() throws Exception {
        // given
        var resource = FilesystemResource.asRoot(tempFile.getPath());

        // when
        var absolutePath = resource.getAbsolutePath();

        // then
        assertThat(absolutePath)
            .isEqualTo(tempFile.getPath());
    }
}
