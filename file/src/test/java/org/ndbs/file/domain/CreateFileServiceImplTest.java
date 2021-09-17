package org.ndbs.file.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.common.testing.IntegrationTest;
import org.ndbs.file.persistent.api.FileRepository;
import org.ndbs.filesystem.domain.filesystem.FileSystemService;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;
import org.ndbs.filesystem.domain.path.model.PathStrategy;
import org.ndbs.filesystem.registry.PathStrategyRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * CreateFileServiceImplTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@DisplayName("CreateFileServiceImpl test: Test for the Create File Service")
@Transactional
class CreateFileServiceImplTest extends IntegrationTest {
    @Autowired
    private FileRepository repository;

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private BuildFilePathService buildFilePathService;

    @Autowired
    private PathStrategyRegistry pathStrategyRegistry;

    @Value("${app.aws.bucket}")
    private String filesystemId;

    private CreateFileServiceImpl createFileService;

    @BeforeEach
    void setUp() throws Exception {
        pathStrategyRegistry.removeStrategy(DefaultPathStrategy.NAME);
        pathStrategyRegistry.addStrategy(new TestPathStrategy());

        createFileService = new CreateFileServiceImpl(
            repository,
            fileSystemService,
            buildFilePathService,
            pathStrategyRegistry,
            filesystemId
        );
    }

    private static class TestPathStrategy implements PathStrategy {
        @Override
        public String getName() {
            return DefaultPathStrategy.NAME;
        }

        @Override
        public String buildPath(org.ndbs.filesystem.domain.filesystem.model.File file) {
            return file.getName();
        }
    }

    @DisplayName("Should create a file in filesystem and save it on a storage")
    @Test
    void shouldCreateFileInFileSystemAndSaveItOnStorage() throws Exception {
        // given
        var imagePathString = getClass().getClassLoader().getResource("data/funny_cat.jpg").getFile();
        var image = new java.io.File(imagePathString);

        var imagePath = image.toPath();

        var imageName = image.getName();
        var imageMimeType = "image/jpg";
        var imageFilesize = Files.size(imagePath);

        try (var imageInputStream = new FileInputStream(image)) {
            var multipartFile = new MockMultipartFile("funny_cat.jpg", "funny_cat.jpg", "image/jpg", imageInputStream);

            // when
            var file = createFileService.create(multipartFile);

            // then
            var fileFoundOptional = repository.findById(file.getId());
            assertThat(fileFoundOptional)
                .isPresent();

            var fileFound = fileFoundOptional.get();
            var filePath = buildFilePathService.build(fileFound);

            assertThat(Files.exists(filePath))
                .isTrue();

            assertThat(fileFound.getId())
                .isNotNull();

            assertThat(fileFound.getFilesystemId())
                .hasToString(filesystemId);

            assertThat(fileFound.getPathStrategy())
                .isInstanceOf(TestPathStrategy.class);

            assertThat(fileFound.getMimeType())
                .isEqualTo(imageMimeType);

            assertThat(fileFound.getName())
                .isEqualTo(imageName);

            assertThat(fileFound.getFilesize())
                .isEqualTo(imageFilesize);

            assertThat(fileFound.isConfirmed())
                .isFalse();

            assertThat(fileFound.isDeleted())
                .isFalse();

            // clean
            Files.delete(filePath);
        }
    }

    @DisplayName("Should not create a file in filesystem when unable to save it on a storage")
    @Test
    void shouldNotCreateFileInFileSystemWhenUnableToSaveItOnStorage() throws Exception {
        // given
        var image = getClass().getClassLoader().getResource("data/funny_cat.jpg").getFile();

        try (var imageInputStream = new FileInputStream(image)) {
            var multipartFile = new MockMultipartFile("funny_cat.jpg", null, "image/jpg", imageInputStream);

            // when / then
            assertThrows(FileException.class, () -> createFileService.create(multipartFile));
        }
    }
}
