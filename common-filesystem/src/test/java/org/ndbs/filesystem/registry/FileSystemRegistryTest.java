package org.ndbs.filesystem.registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.filesystem.FileSystemNotFoundException;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.domain.filesystem.model.LocalFileSystem;

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

    @DisplayName("Should not has local filesystem by default")
    @Test
    void shouldNotHasLocalFileSystemByDefault() throws Exception {
        // when
        var hasLocalFileSystem = registry.hasFileSystem(FileSystemId.local());

        // then
        assertThat(hasLocalFileSystem)
            .isFalse();
    }

    @DisplayName("Should successfully add a new filesystem")
    @Test
    void shouldSuccessfullyAddNewFileSystem() throws Exception {
        // given
        var expectedFileSystem = LocalFileSystem.create();

        // when
        registry.addFileSystem(expectedFileSystem);
        var hasLocalFileSystem = registry.hasFileSystem(expectedFileSystem.getFileSystemId());

        // then
        assertThat(hasLocalFileSystem)
            .isTrue();
    }

    @DisplayName("Should throw an exception when registry does not contain a filesystem")
    @Test
    void shouldThrowExceptionWhenRegistryDoesNotContainFileSystem() throws Exception {
        // when / then
        assertThrows(FileSystemNotFoundException.class, () -> registry.getFileSystem(FileSystemId.local()));
    }
}
