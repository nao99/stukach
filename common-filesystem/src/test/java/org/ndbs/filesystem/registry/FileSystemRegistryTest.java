package org.ndbs.filesystem.registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.filesystem.FileSystemNotFoundException;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;

import java.nio.file.FileSystems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * FileSystemRegistryTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
@DisplayName("FileSystemRegistry test: Test for the File System Registry")
class FileSystemRegistryTest {
    private FileSystemRegistry registry;

    @BeforeEach
    public void setUp() throws Exception {
        registry = FileSystemRegistry.create();
    }

    @DisplayName("Should not has any filesystems by default")
    @Test
    void shouldNotHasAnyFileSystemsByDefault() throws Exception {
        // when / then
        assertThat(registry.empty())
            .isTrue();
    }

    @DisplayName("Should successfully add a new filesystem")
    @Test
    void shouldSuccessfullyAddNewFileSystem() throws Exception {
        // given
        var fileSystemId = FileSystemId.create("local");
        var fileSystem = FileSystems.getDefault();

        // when
        registry.addFileSystem(fileSystemId, fileSystem);

        // then
        assertThat(registry.hasFileSystem(fileSystemId))
            .isTrue();
    }

    @DisplayName("Should throw an exception when registry does not contain a filesystem")
    @Test
    void shouldThrowExceptionWhenRegistryDoesNotContainFileSystem() throws Exception {
        // when / then
        assertThrows(FileSystemNotFoundException.class, () -> registry.getFileSystem(FileSystemId.create("local")));
    }
}
