package org.ndbs.filesystem.domain.filesystem.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * FileSystemIdTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
@DisplayName("FileSystemId test: Test for the Filesystem Id class")
class FileSystemIdTest {
    @DisplayName("Should create a filesystem id with \"s3\" id and \"s3\" scheme")
    @Test
    void shouldCreateFileSystemIdWithS3IdAndS3Scheme() throws Exception {
        // given
        var expectedFileSystemIdString = "s3";
        var expectedFileSystemIdScheme = "s3";

        // when
        var fileSystemId = FileSystemId.create("s3", expectedFileSystemIdScheme);

        // then
        assertThat(fileSystemId.getId())
            .isEqualTo(expectedFileSystemIdString);

        assertThat(fileSystemId.getScheme())
            .isEqualTo(expectedFileSystemIdScheme);
    }

    @DisplayName("Should throw an exception when passed id is null")
    @Test
    void shouldThrowExceptionWhenPassedIdIsNull() throws Exception {
        // when / then
        assertThrows(IllegalArgumentException.class, () -> FileSystemId.create(null, "file"));
    }

    @DisplayName("Should throw an exception when passed scheme is null")
    @Test
    void shouldThrowExceptionWhenPassedSchemeIsNull() throws Exception {
        // when / then
        assertThrows(IllegalArgumentException.class, () -> FileSystemId.create("local", null));
    }

    @DisplayName("Should create a local filesystem id")
    @Test
    void shouldCreateLocalFileSystemId() throws Exception {
        // given
        var expectedFileSystemIdString = "local";

        // when
        var fileSystemId = FileSystemId.createLocal();

        // then
        assertThat(fileSystemId.getId())
            .isEqualTo(expectedFileSystemIdString);
    }
}
