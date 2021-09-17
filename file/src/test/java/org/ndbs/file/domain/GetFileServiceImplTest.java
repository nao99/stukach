package org.ndbs.file.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.common.testing.IntegrationTest;
import org.ndbs.file.domain.model.File;
import org.ndbs.file.persistent.api.FileRepository;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * GetFileServiceImplTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@DisplayName("GetFileServiceImplTest")
@Transactional
class GetFileServiceImplTest extends IntegrationTest {
    @Autowired
    private FileRepository repository;

    private GetFileServiceImpl getFileService;

    @BeforeEach
    void setUp() throws Exception {
        getFileService = new GetFileServiceImpl(repository);
    }

    @DisplayName("Should get a file")
    @Test
    void shouldGetFile() throws Exception {
        // given
        var file = File.create(
            FileSystemId.create("local"),
            DefaultPathStrategy.create(),
            "application/mp4",
            "file.mp4",
            200
        );

        var savedFile = repository.save(file);

        // when
        var foundFile = getFileService.getFile(savedFile.getId());

        // then
        assertThat(foundFile.getId())
            .isEqualTo(savedFile.getId());
    }

    @DisplayName("Should throw an exception when file was not found")
    @Test
    void shouldThrowExceptionWhenFileWasNotFound() throws Exception {
        // when / then
        assertThrows(FileNotFoundException.class, () -> getFileService.getFile(UUID.randomUUID()));
    }
}
