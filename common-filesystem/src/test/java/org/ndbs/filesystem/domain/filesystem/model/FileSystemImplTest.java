package org.ndbs.filesystem.domain.filesystem.model;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.filesystem.FileSystemIOException;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * FileSystemImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
@DisplayName("FileSystemImpl test: Test for the Local File System class")
class FileSystemImplTest {
    private File tempFile;
    private FileSystemImpl fileSystem;

    @BeforeEach
    void setUp() throws Exception {
        tempFile = File.createTempFile("awesome_file", ".mp4");
        fileSystem = FileSystemImpl.create();
    }

    @AfterEach
    void tearDown() throws Exception {
        tempFile.delete();
    }

    @DisplayName("Should has a file by path")
    @Test
    void shouldHasFileByPath() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        // when
        boolean fileSystemHasTempFile = fileSystem.has(tempFilePath);

        // then
        assertThat(fileSystemHasTempFile)
            .isTrue();
    }

    @DisplayName("Should not has a file by path")
    @Test
    void shouldNotHasFileByPath() throws Exception {
        // given
        var tempFilePath = Path.of("/random_path_which/should_not_exists.mp4");

        // when
        boolean fileSystemHasTempFile = fileSystem.has(tempFilePath);

        // then
        assertThat(fileSystemHasTempFile)
            .isFalse();
    }

    @DisplayName("Should get a file content stream of existed file")
    @Test
    void shouldGetFileContentStreamOfExistedFile() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();
        try (var tempFileStream = fileSystem.read(tempFilePath)) {
            // when
            var fileToWritePathString = buildTempFilePath("awesome_file_copy.mp4");
            var fileToWrite = new File(fileToWritePathString);

            FileUtils.copyInputStreamToFile(tempFileStream, fileToWrite);

            // then
            boolean filesAreEqual = FileUtils.contentEquals(tempFile, fileToWrite);

            assertThat(filesAreEqual)
                .isTrue();

            // clean
            fileToWrite.delete();
        }
    }

    @DisplayName("Should throw an exception if file does not exist by passed path when reading")
    @Test
    void shouldThrowExceptionIfFileDoesNotExistByPassedPathWhenReading() throws Exception {
        // given
        var incorrectFilePath = Path.of("/incorrect_file/path.mp4");

        // when / then
        assertThrows(FileSystemIOException.class, () -> fileSystem.read(incorrectFilePath));
    }

    @DisplayName("Should write a file content by a path")
    @Test
    void shouldWriteFileContentByPath() throws Exception {
        // given
        var fileToWritePathString = buildTempFilePath("test.mp4");
        var filePathToWrite = Path.of(fileToWritePathString);

        try (var fileContentStream = new FileInputStream(tempFile)) {
            // when
            fileSystem.write(filePathToWrite, fileContentStream);
            var writtenFile = filePathToWrite.toFile();

            // then
            assertThat(writtenFile)
                .isNotNull();

            assertThat(writtenFile)
                .exists();

            // clean
            writtenFile.delete();
        }
    }

    @DisplayName("Should throw an exception if a file exists by passed path when writing")
    @Test
    void shouldThrowExceptionIfFileExistsBePassedPathWhenWriting() throws Exception {
        // given
        var fileToWritePathString = buildTempFilePath("test.mp4");
        var filePathToWrite = Path.of(fileToWritePathString);

        try (var fileContentStream = new FileInputStream(tempFile)) {
            fileSystem.write(filePathToWrite, fileContentStream);
            var writtenFile = filePathToWrite.toFile();

            // when / then
            assertThrows(FileSystemIOException.class, () -> fileSystem.write(filePathToWrite, fileContentStream));

            // clean
            writtenFile.delete();
        }
    }

    @DisplayName("Should rename a file")
    @Test
    void shouldRenameFile() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        var tempFilePathAfterRenamingString = buildTempFilePath("test.mp4");
        var tempFilePathAfterRenaming = Path.of(tempFilePathAfterRenamingString);

        // when
        fileSystem.rename(tempFilePath, tempFilePathAfterRenaming);
        var tempFileAfterRenaming = new File(tempFilePathAfterRenamingString);

        // then
        assertThat(tempFile)
            .doesNotExist();

        assertThat(tempFileAfterRenaming)
            .exists();

        // clean
        tempFileAfterRenaming.delete();
    }

    @DisplayName("Should not rename a file if passed paths are equal")
    @Test
    void shouldNotRenameFileIfPassedPathsAreEqual() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        // when / then
        assertDoesNotThrow(() -> fileSystem.rename(tempFilePath, tempFilePath));

        assertThat(tempFile)
            .exists();
    }

    @DisplayName("Should throw an exception if file does not exist by passed old path when renaming")
    @Test
    void shouldThrowExceptionIfFileDoesNotExistByPassedOldPathWhenRenaming() throws Exception {
        // given
        var incorrectOldFilePath = Path.of("/incorrect_file/path.mp4");

        var correctNewFilePathString = buildTempFilePath("test.mp4");
        var correctNewFilePath = Path.of(correctNewFilePathString);

        // when / then
        assertThrows(
            FileSystemIOException.class,
            () -> fileSystem.rename(incorrectOldFilePath, correctNewFilePath)
        );
    }

    @DisplayName("Should throw an exception if a file exists by passed new path when renaming")
    @Test
    void shouldThrowExceptionIfFileExistsByPassedNedPathWhenRenaming() throws Exception {
        // given
        var correctOldFilePath = tempFile.toPath();

        var correctNewFilePathString = buildTempFilePath("test.mp4");
        var correctNewFilePath = Path.of(correctNewFilePathString);

        var tempFileCopy = new File(correctNewFilePathString);
        FileUtils.copyFile(tempFile, tempFileCopy);

        // when / then
        assertThrows(FileSystemIOException.class, () -> fileSystem.rename(correctOldFilePath, correctNewFilePath));

        // clean
        tempFileCopy.delete();
    }

    @DisplayName("Should copy a file")
    @Test
    void shouldCopyFile() throws Exception {
        // given
        var correctOldFilePath = tempFile.toPath();

        var correctNewFilePathString = buildTempFilePath("test.mp4");
        var correctNewFilePath = Path.of(correctNewFilePathString);

        var tempFileCopy = new File(correctNewFilePathString);

        // when
        fileSystem.copy(correctOldFilePath, correctNewFilePath);

        // then
        assertThat(tempFile)
            .exists();

        assertThat(tempFileCopy)
            .exists();

        boolean filesAreEqual = FileUtils.contentEquals(tempFile, tempFileCopy);
        assertThat(filesAreEqual)
            .isTrue();

        // clean
        tempFileCopy.delete();
    }

    @DisplayName("Should not copy a file if passed paths are equal")
    @Test
    void shouldNotCopyFileIfPassedPathsAreEqual() throws Exception {
        // given
        var correctOldFilePath = tempFile.toPath();

        // when / then
        assertDoesNotThrow(() -> fileSystem.copy(correctOldFilePath, correctOldFilePath));

        assertThat(tempFile)
            .exists();
    }

    @DisplayName("Should throw an exception if file does not exist by passed old path when copying")
    @Test
    void shouldThrowExceptionIfFileDoesNotExistByPassedOldPathWhenCopying() throws Exception {
        // given
        var incorrectOldFilePath = Path.of("/incorrect/path.mp4");

        var correctNewFilePathString = buildTempFilePath("test.mp4");
        var correctNewFilePath = Path.of(correctNewFilePathString);

        // when / then
        assertThrows(FileSystemIOException.class, () -> fileSystem.copy(incorrectOldFilePath, correctNewFilePath));
    }

    @DisplayName("Should throw an exception if a file exists by passed new path when copying")
    @Test
    void shouldThrowExceptionIfFileExistsByPassedNedPathWhenCopying() throws Exception {
        // given
        var correctOldFilePath = tempFile.toPath();

        var correctNewFilePathString = buildTempFilePath("test.mp4");
        var correctNewFilePath = Path.of(correctNewFilePathString);

        var tempFileCopy = new File(correctNewFilePathString);
        FileUtils.copyFile(tempFile, tempFileCopy);

        // when / then
        assertThrows(FileSystemIOException.class, () -> fileSystem.copy(correctOldFilePath, correctNewFilePath));

        // clean
        tempFileCopy.delete();
    }

    @DisplayName("Should delete a file")
    @Test
    void shouldDeleteFile() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        // when
        fileSystem.delete(tempFilePath);

        // then
        assertThat(tempFile)
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if a file does not exist by passed path when deleting")
    @Test
    void shouldThrowExceptionIfFileDoesNotExistByPassedPathWhenDeleting() throws Exception {
        // given
        var incorrectFilePath = Path.of("/incorrect/path.mp4");

        // when / then
        assertThrows(FileSystemIOException.class, () -> fileSystem.delete(incorrectFilePath));
    }

    @DisplayName("Should get a filesize of file")
    @Test
    void shouldGetFilesizeOfFile() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();
        var expectedFilesize = FileUtils.sizeOf(tempFile);

        // when
        var filesize = fileSystem.getSize(tempFilePath);

        // then
        assertThat(filesize)
            .isEqualTo(expectedFilesize);
    }

    @DisplayName("Should throw an exception if a file does not exist by passed path when getting filesize")
    @Test
    void shouldThrowExceptionIfFileDoesNotExistByPassedPathWhenGettingFilesize() throws Exception {
        // given
        var incorrectFilePath = Path.of("/incorrect/path.mp4");

        // when / then
        assertThrows(FileSystemIOException.class, () -> fileSystem.getSize(incorrectFilePath));
    }

    private String buildTempFilePath(String filename) {
        var tempFileParent = tempFile.getParentFile();
        var tempFileParentDirectoryPath = tempFileParent.getName();

        return String.format("/%s/%s", tempFileParentDirectoryPath, filename);
    }
}
