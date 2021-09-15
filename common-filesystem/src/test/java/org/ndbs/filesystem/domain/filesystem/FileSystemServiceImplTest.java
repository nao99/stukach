package org.ndbs.filesystem.domain.filesystem;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * FileSystemServiceImplTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
@DisplayName("FileSystemServiceImpl test: Test for the File System Service Impl")
class FileSystemServiceImplTest {
    private File tempFile;
    private FileSystemServiceImpl fileSystemService;

    @BeforeEach
    void setUp() throws Exception {
        tempFile = File.createTempFile("awesome_file", ".mp4");
        fileSystemService = FileSystemServiceImpl.create();
    }

    @AfterEach
    void tearDown() throws Exception {
        tempFile.delete();
    }

    @DisplayName("Should create a copy of file if it exists by a file path and does not exist by destination path")
    @Test
    void shouldCreateCopyOfFileIfItExistsByFilePathAndDoesNotExistByDestinationPath() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        var tempFilePathToCopyString = buildTempFilePath("test.mp4");
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        var tempFileCopy = new File(tempFilePathToCopyString);

        // when / then
        assertDoesNotThrow(() -> fileSystemService.copy(tempFilePath, tempFilePathToCopy));

        assertThat(tempFile)
            .exists();

        assertThat(tempFileCopy)
            .exists();

        assertThat(FileUtils.contentEquals(tempFile, tempFileCopy))
            .isTrue();

        // clean
        tempFileCopy.delete();
    }

    @DisplayName("Should create a copy of file and parent directories if they do not exist")
    @Test
    void shouldCreateCopyOfFileAndParentDirectoriesIfTheyDoNoExist() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        var tempFileParent = tempFile.getParentFile();
        var tempFileParentDirectoryPath = tempFileParent.getName();

        var tempFilePathToCopyString = String.format("/%s/unknown_directory/test.mp4", tempFileParentDirectoryPath);
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        var tempFileCopy = new File(tempFilePathToCopyString);
        var tempDirectory = tempFilePathToCopy.getParent().toFile();

        // when / then
        assertDoesNotThrow(() -> fileSystemService.copy(tempFilePath, tempFilePathToCopy));

        assertThat(tempFile)
            .exists();

        assertThat(tempFileCopy)
            .exists();

        assertThat(FileUtils.contentEquals(tempFile, tempFileCopy))
            .isTrue();

        // clean
        tempFileCopy.delete();
        tempDirectory.delete();
    }

    @DisplayName("Should not create any copies if file paths are equal")
    @Test
    void shouldNotCreateAnyCopiesIfFilePathsAreEqual() throws Exception {
        // given
        var tempFilePathToCopyString = buildTempFilePath("test2.mp4");
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        // when / then
        assertDoesNotThrow(() -> fileSystemService.copy(tempFilePathToCopy, tempFilePathToCopy));

        assertThat(tempFilePathToCopy)
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if a file was not found by passed file path")
    @Test
    void shouldThrowExceptionIfFileWasNotFoundByPassedFilePath() throws Exception {
        // given
        var tempFilePathString = buildTempFilePath("test1.mp4");
        var tempFilePath = Path.of(tempFilePathString);

        var tempFilePathToCopyString = buildTempFilePath("test2.mp4");
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        // when / then
        assertThrows(
            FileSystemResourceNotFoundException.class,
            () -> fileSystemService.copy(tempFilePath, tempFilePathToCopy)
        );

        assertThat(tempFilePath)
            .doesNotExist();

        assertThat(tempFilePathToCopy)
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if by passed file path was found a not file")
    @Test
    void shouldThrowExceptionIfByPassedFilePathWasFoundNotFile() throws Exception {
        // given
        var tempFilePathString = buildTempFilePath("test1.mp4");

        var tempFilePath = Path.of(tempFilePathString);
        var tempRootDirectoryPath = tempFilePath.getRoot();

        var tempFilePathToCopyString = buildTempFilePath("test2.mp4");
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        // when / then
        assertThrows(
            FileSystemResourceIsNotFileException.class,
            () -> fileSystemService.copy(tempRootDirectoryPath, tempFilePathToCopy)
        );

        assertThat(tempFilePath)
            .doesNotExist();

        assertThat(tempFilePathToCopy)
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if by passed destination path was found a not file")
    @Test
    void shouldThrowExceptionIfByPassedDestinationPathWasFoundNotFile() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();
        var tempFilePathToCopyString = buildTempFilePath("test2.mp4");

        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);
        var tempRootDirectoryToCopyPath = tempFilePath.getRoot();

        // when / then
        assertThrows(
            FileSystemResourceIsNotFileException.class,
            () -> fileSystemService.copy(tempFilePath, tempRootDirectoryToCopyPath)
        );

        assertThat(tempFilePath)
            .exists();

        assertThat(tempFilePathToCopy)
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if a file exists at the destination path")
    @Test
    void shouldThrowExceptionIfFileExistsAtDestinationPath() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        var tempFilePathToCopyString = buildTempFilePath("test.mp4");
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        var tempFileCopy = new File(tempFilePathToCopyString);

        FileUtils.copyFile(tempFile, tempFileCopy);

        // when / then
        assertThrows(
            FileSystemResourceExistsException.class,
            () -> fileSystemService.copy(tempFilePath, tempFilePathToCopy)
        );

        assertThat(tempFilePathToCopy)
            .exists();

        // clean
        tempFileCopy.delete();
    }

    @DisplayName("Should create a copy of file stream if it does not exist by destination path")
    @Test
    void shouldCreateCopyOfFileStreamIfItDoesNotExistByDestinationPath() throws Exception {
        // given
        var tempFileStream = new FileInputStream(tempFile);

        var tempFilePathToCopyString = buildTempFilePath("test.mp4");
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        var tempFileCopy = new File(tempFilePathToCopyString);

        // when / then
        assertDoesNotThrow(() -> fileSystemService.copy(tempFileStream, tempFilePathToCopy));

        assertThat(tempFile)
            .exists();

        assertThat(tempFileCopy)
            .exists();

        assertThat(FileUtils.contentEquals(tempFile, tempFileCopy))
            .isTrue();

        // clean
        tempFileCopy.delete();
    }

    @DisplayName("Should create a copy of file stream and parent directories if they do not exist")
    @Test
    void shouldCreateCopyOfFileStreamAndParentDirectoriesIfTheyDoNoExist() throws Exception {
        // given
        var tempFileStream = new FileInputStream(tempFile);

        var tempFileParent = tempFile.getParentFile();
        var tempFileParentDirectoryPath = tempFileParent.getName();

        var tempFilePathToCopyString = String.format("/%s/unknown_directory/test.mp4", tempFileParentDirectoryPath);
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        var tempFileCopy = new File(tempFilePathToCopyString);
        var tempDirectory = tempFilePathToCopy.getParent().toFile();

        // when / then
        assertDoesNotThrow(() -> fileSystemService.copy(tempFileStream, tempFilePathToCopy));

        assertThat(tempFile)
            .exists();

        assertThat(tempFileCopy)
            .exists();

        assertThat(FileUtils.contentEquals(tempFile, tempFileCopy))
            .isTrue();

        // clean
        tempFileCopy.delete();
        tempDirectory.delete();
    }

    @DisplayName("Should throw an exception if by passed destination path was found a not file in stream case")
    @Test
    void shouldThrowExceptionIfByPassedDestinationPathWasFoundNotFileInStreamCase() throws Exception {
        // given
        var tempFileStream = new FileInputStream(tempFile);

        var tempFilePath = tempFile.toPath();
        var tempFilePathToCopyString = buildTempFilePath("test2.mp4");

        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);
        var tempRootDirectoryToCopyPath = tempFilePath.getRoot();

        // when / then
        assertThrows(
            FileSystemResourceIsNotFileException.class,
            () -> fileSystemService.copy(tempFileStream, tempRootDirectoryToCopyPath)
        );

        assertThat(tempFilePath)
            .exists();

        assertThat(tempFilePathToCopy)
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if a file exists at the destination path in stream case")
    @Test
    void shouldThrowExceptionIfFileExistsAtDestinationPathInStreamCase() throws Exception {
        // given
        var tempFileStream = new FileInputStream(tempFile);

        var tempFilePathToCopyString = buildTempFilePath("test.mp4");
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        var tempFileCopy = new File(tempFilePathToCopyString);

        FileUtils.copyFile(tempFile, tempFileCopy);

        // when / then
        assertThrows(
            FileSystemResourceExistsException.class,
            () -> fileSystemService.copy(tempFileStream, tempFilePathToCopy)
        );

        assertThat(tempFilePathToCopy)
            .exists();

        // clean
        tempFileCopy.delete();
    }

    @DisplayName("Should rename a file if it exists by a resource path and does not exist by destination path")
    @Test
    void shouldRenameFileIfItExistsByResourcePathAndDoesNotExistByDestinationPath() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        var tempFileNewPathString = buildTempFilePath("test.mp4");
        var tempFileNewPath = Path.of(tempFileNewPathString);

        var tempFileNew = new File(tempFileNewPathString);

        // when / then
        assertDoesNotThrow(() -> fileSystemService.rename(tempFilePath, tempFileNewPath));

        assertThat(tempFile)
            .doesNotExist();

        assertThat(tempFileNew)
            .exists();

        // clean
        tempFileNew.delete();
    }

    @DisplayName("Should rename a directory if it exists by a resource path and does not exist by destination path")
    @Test
    void shouldRenameDirectoryIfItExistsByResourcePathAndDoesNotExistByDestinationPath() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        var tempFileParent = tempFile.getParentFile();
        var tempFileParentDirectoryPath = tempFileParent.getName();

        var tempFilePathToCopyString = String.format("/%s/unknown_directory/test.mp4", tempFileParentDirectoryPath);
        var tempFilePathToCopy = Path.of(tempFilePathToCopyString);

        var tempFileCopy = new File(tempFilePathToCopyString);

        fileSystemService.copy(tempFilePath, tempFilePathToCopy);

        var tempDirectoryPath = tempFilePathToCopy.getParent();
        tempFileCopy.delete();

        var tempDirectoryNewName = String.format("/%s/unknown_directory_renamed", tempFileParentDirectoryPath);
        var tempDirectoryNewPath = Path.of(tempDirectoryNewName);

        // when / then
        assertDoesNotThrow(() -> fileSystemService.rename(tempDirectoryPath, tempDirectoryNewPath));

        assertThat(tempFile)
            .exists();

        assertThat(tempFileCopy)
            .doesNotExist();

        assertThat(tempDirectoryPath)
            .doesNotExist();

        assertThat(tempDirectoryNewPath)
            .exists();

        // clean
        var tempDirectoryNewFile = tempDirectoryNewPath.toFile();
        tempDirectoryNewFile.delete();
    }

    @DisplayName("Should not rename a resource if resource and destination parent paths are different")
    @Test
    void shouldNotRenameResourceIfResourceAndDestinationParentPathsAreDifferent() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        var tempFileParent = tempFile.getParentFile();
        var tempFileParentDirectoryPath = tempFileParent.getName();

        var tempFileNewPathString = String.format("/%s/unknown_directory/test.mp4", tempFileParentDirectoryPath);
        var tempFileNewPath = Path.of(tempFileNewPathString);

        // when / then
        assertThrows(
            FileSystemResourceParentPathsAreDifferentException.class,
            () -> fileSystemService.rename(tempFilePath, tempFileNewPath)
        );

        assertThat(tempFilePath)
            .exists();

        assertThat(tempFileNewPath)
            .doesNotExist();

        assertThat(tempFileNewPath.getParent())
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if a resource was not found by passed path in renaming case")
    @Test
    void shouldThrowExceptionIfResourceWasNotFoundByPassedPathInRenamingCase() throws Exception {
        // given
        var tempFilePathString = buildTempFilePath("test1.mp4");
        var tempFilePath = Path.of(tempFilePathString);

        var tempFileNewPathString = buildTempFilePath("test.mp4");
        var tempFileNewPath = Path.of(tempFileNewPathString);

        // when / then
        assertThrows(
            FileSystemResourceNotFoundException.class,
            () -> fileSystemService.rename(tempFilePath, tempFileNewPath)
        );

        assertThat(tempFilePath)
            .doesNotExist();

        assertThat(tempFileNewPath)
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if a resource exists at the destination path in renaming case")
    @Test
    void shouldThrowExceptionIfResourceExistsAtDestinationPathInRenamingCase() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        var tempFileNewPathString = buildTempFilePath("test.mp4");
        var tempFileNewPath = Path.of(tempFileNewPathString);

        var tempFileCopy = new File(tempFileNewPathString);

        FileUtils.copyFile(tempFile, tempFileCopy);

        // when / then
        assertThrows(
            FileSystemResourceExistsException.class,
            () -> fileSystemService.rename(tempFilePath, tempFileNewPath)
        );

        assertThat(tempFileNewPath)
            .exists();

        // clean
        tempFileCopy.delete();
    }

    @DisplayName("Should delete a file if it exists")
    @Test
    void shouldDeleteFileIfItExists() throws Exception {
        // given
        var tempFilePath = tempFile.toPath();

        // when / then
        assertThat(tempFilePath)
            .exists();

        assertDoesNotThrow(() -> fileSystemService.delete(tempFilePath));

        assertThat(tempFilePath)
            .doesNotExist();
    }

    @DisplayName("Should delete a directory if it exists")
    @Test
    void shouldDeleteDirectoryIfItExists() throws Exception {
        // given
        var tempFileParent = tempFile.getParentFile();
        var tempFileParentDirectoryPath = tempFileParent.getName();

        var tempDirectoryPathString = String.format("/%s/unknown_directory", tempFileParentDirectoryPath);
        var tempDirectoryPath = Path.of(tempDirectoryPathString);

        // when / then
        Files.createDirectory(tempDirectoryPath);

        assertThat(tempDirectoryPath)
            .exists();

        assertDoesNotThrow(() -> fileSystemService.delete(tempDirectoryPath));

        assertThat(tempDirectoryPath)
            .doesNotExist();
    }

    @DisplayName("Should throw an exception if resource does not exist in deleting case")
    @Test
    void shouldThrowExceptionIfResourceDoesNotExistInDeletingCase() throws Exception {
        // given
        var nonExistedFilePath = Path.of("unknown_file.mp4");

        // when / then
        assertThat(nonExistedFilePath)
            .doesNotExist();

        assertThrows(
            FileSystemResourceNotFoundException.class,
            () -> fileSystemService.delete(nonExistedFilePath)
        );

        assertThat(nonExistedFilePath)
            .doesNotExist();
    }

    @DisplayName("Should move a file via copying and deleting methods")
    @Test
    void shouldMoveFileViaCopyingAndDeletingMethods() throws Exception {
        // given
        fileSystemService = mock(FileSystemServiceImpl.class);

        var tempFilePath = tempFile.toPath();

        var tempFilePathToMoveString = buildTempFilePath("test.mp4");
        var tempFilePathToMove = Path.of(tempFilePathToMoveString);

        // when / then
        doNothing()
            .when(fileSystemService)
            .copy(tempFilePath, tempFilePathToMove);

        doNothing()
            .when(fileSystemService)
            .delete(tempFilePath);

        doCallRealMethod()
            .when(fileSystemService)
            .move(tempFilePath, tempFilePathToMove);

        fileSystemService.move(tempFilePath, tempFilePathToMove);

        verify(fileSystemService, times(1))
            .copy(tempFilePath, tempFilePathToMove);

        verify(fileSystemService, times(1))
            .delete(tempFilePath);
    }

    private String buildTempFilePath(String filename) {
        var tempFileParent = tempFile.getParentFile();
        var tempFileParentDirectoryPath = tempFileParent.getName();

        return String.format("/%s/%s", tempFileParentDirectoryPath, filename);
    }
}
