package org.ndbs.filesystem.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.filesystem.FileSystemNotFoundException;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;

import java.net.URI;
import java.nio.file.FileSystems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * FileSystemUtilsTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@DisplayName("FileSystemUtils test: Test for the File System Utils")
class FileSystemUtilsTest {
    @DisplayName("Should return a unix filesystem")
    @Test
    void shouldReturnUnixFileSystem() throws Exception {
        // given
        var fileSystemId = FileSystemId.createLocal();
        var expectedFileSystem = FileSystems.getFileSystem(URI.create("file:///"));

        // when
        var fileSystem = FileSystemUtils.getFileSystemBy(fileSystemId);

        // then
        assertThat(fileSystem)
            .isEqualTo(expectedFileSystem);
    }

    @DisplayName("Should throw an exception when filesystem was not found")
    @Test
    void shouldThrowExceptionWhenFileSystemWasNotFound() throws Exception {
        // given
        var fileSystemId = FileSystemId.create("unknown_filesystem_id", "unknown");

        // when / then
        assertThrows(FileSystemNotFoundException.class, () -> FileSystemUtils.getFileSystemBy(fileSystemId));
    }
}
