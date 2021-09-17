package org.ndbs.file.domain;

import com.upplication.s3fs.S3FileSystem;
import com.upplication.s3fs.S3Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.file.domain.model.File;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;
import org.ndbs.filesystem.domain.path.model.PathStrategy;
import org.ndbs.filesystem.registry.FileSystemRegistry;

import java.net.URI;
import java.nio.file.FileSystems;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * BuildFilePathServiceImplTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-15
 */
@DisplayName("BuildFilePathServiceImpl: Test for the Build File Path Service Impl")
class BuildFilePathServiceImplTest {
    private FileSystemRegistry fileSystemRegistry;
    private BuildFilePathServiceImpl buildFilePathService;

    @BeforeEach
    void setUp() throws Exception {
        fileSystemRegistry = FileSystemRegistry.create();
        fileSystemRegistry.addFileSystem(FileSystemId.create("local"), FileSystems.getDefault());

        buildFilePathService = new BuildFilePathServiceImpl(fileSystemRegistry);
    }

    private static class TestPathStrategy implements PathStrategy {
        @Override
        public String getName() {
            return "test";
        }

        @Override
        public String buildPath(org.ndbs.filesystem.domain.filesystem.model.File file) {
            return file.getName();
        }
    }

    @DisplayName("Should return path to \"s3-default\" filesystem")
    @Test
    void shouldReturnPathToS3DefaultFileSystem() throws Exception {
        // given
        var s3FileSystemId = FileSystemId.create("s3-default");

        var s3Uri = URI.create("s3:///");
        var s3FileSystem = (S3FileSystem) FileSystems.newFileSystem(s3Uri, null);

        fileSystemRegistry.addFileSystem(s3FileSystemId, s3FileSystem);

        var defaultPathStrategy = new TestPathStrategy();
        var file = File.create(s3FileSystemId, defaultPathStrategy, "application/mp4", "file.mp4", 200);

        var expectedFilePathString = defaultPathStrategy.buildPath(file);
        var expectedFilePath = new S3Path(s3FileSystem, "/s3-default", expectedFilePathString);

        // when
        var filePath = buildFilePathService.build(file);

        // then
        assertThat(filePath)
            .isEqualTo(expectedFilePath);
    }

    @DisplayName("Should return path to \"local\" filesystem")
    @Test
    void shouldReturnPathToLocalFileSystem() throws Exception {
        // given
        var fileSystemId = FileSystemId.create("/");
        var fileSystem = FileSystems.getDefault();

        fileSystemRegistry.addFileSystem(fileSystemId, fileSystem);

        var pathStrategy = new TestPathStrategy();

        var file = File.create(fileSystemId, pathStrategy, "application/mp4", "file.mp4", 200);
        var filePathString = pathStrategy.buildPath(file);

        var expectedFilePath = fileSystem.getPath("/", filePathString);

        // when
        var filePath = buildFilePathService.build(file);

        // then
        assertThat(filePath)
            .isEqualTo(expectedFilePath);
    }

    @DisplayName("Should throw an exception if unable to find a filesystem by specified id")
    @Test
    void shouldThrowExceptionIfUnableToFindFileSystemBySpecifiedId() throws Exception {
        // given
        var fileSystemId = FileSystemId.create("unknown_filesystem_id");
        var defaultPathStrategy = DefaultPathStrategy.create();

        var file = File.create(fileSystemId, defaultPathStrategy, "application/mp4", "file.mp4", 200);

        // when / then
        assertThrows(FileSystemNotFoundException.class, () -> buildFilePathService.build(file));
    }

    @DisplayName("Should throw an exception if a file has nullable id")
    @Test
    void shouldThrowExceptionIfFileHasNullableId() throws Exception {
        // given
        var fileSystemId = FileSystemId.create("local");
        var fileSystem = FileSystems.getDefault();

        fileSystemRegistry.addFileSystem(fileSystemId, fileSystem);

        var defaultPathStrategy = DefaultPathStrategy.create();
        var file = File.create(fileSystemId, defaultPathStrategy, "application/mp4", "file.mp4", 200);

        // when / then
        assertThrows(FilePathBuildingException.class, () -> buildFilePathService.build(file));
    }
}
