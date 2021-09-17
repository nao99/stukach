package org.ndbs.file.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.common.testing.IntegrationTest;
import org.ndbs.file.domain.model.File;
import org.ndbs.file.persistent.api.FileRepository;
import org.ndbs.filesystem.domain.filesystem.FileSystemService;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * DeleteFileServiceImplTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-17
 */
@DisplayName("DeleteFileServiceImpl test: Test for the Delete File Service Impl")
@Transactional
class DeleteFileServiceImplTest extends IntegrationTest {
    @Autowired
    private FileRepository repository;

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private BuildFilePathService buildFilePathService;

    @Autowired
    private CreateFileService createFileService;

    private DeleteFileServiceImpl deleteFileService;

    @BeforeEach
    void setUp() throws Exception {
        deleteFileService = new DeleteFileServiceImpl(repository, fileSystemService, buildFilePathService);
    }

    @DisplayName("Should delete a file from database and storage")
    @Test
    void shouldDeleteFileFromDatabaseAndStorage() throws Exception {
        // given
        var imagePathString = getClass().getClassLoader().getResource("data/funny_cat.jpg").getFile();
        var image = new java.io.File(imagePathString);

        try (var imageInputStream = new FileInputStream(image)) {
            var multipartFile = new MockMultipartFile("funny_cat.jpg", "funny_cat.jpg", "image/jpg", imageInputStream);
            var file = createFileService.create(multipartFile);

            // when
            var filePath = buildFilePathService.build(file);
            assertThat(Files.exists(filePath))
                .isTrue();

            deleteFileService.delete(file);
            var fileFoundOptional = repository.findById(file.getId());

            // then
            assertThat(fileFoundOptional)
                .isNotPresent();

            assertThat(Files.exists(filePath))
                .isFalse();
        }
    }

    @DisplayName("Should not throw any exception if file exists in database and does not exist in storage")
    @Test
    void shouldNotThrowAnyExceptionIfFileExistsInDatabaseAndDoesNotExistInStorage() throws Exception {
        // given
        var fileSystemId = FileSystemId.create("s3-default");
        var pathStrategy = DefaultPathStrategy.create();

        var file = File.create(fileSystemId, pathStrategy, "image/jpg", "funny_cat.jpg", 8500);
        var fileSaved = repository.save(file);

        // when / then
        var filePath = buildFilePathService.build(fileSaved);
        assertThat(Files.exists(filePath))
            .isFalse();

        assertDoesNotThrow(() -> deleteFileService.delete(fileSaved));

        var fileFoundOptional = repository.findById(fileSaved.getId());
        assertThat(fileFoundOptional)
            .isNotPresent();

        assertThat(Files.exists(filePath))
            .isFalse();
    }
}
