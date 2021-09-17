package org.ndbs.file.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.file.domain.*;
import org.ndbs.file.domain.model.File;
import org.ndbs.file.persistent.data.convert.FileSystemIdToStringConverter;
import org.ndbs.file.persistent.data.convert.PathStrategyToStringConverter;
import org.ndbs.file.persistent.data.convert.StringToFileSystemIdConverter;
import org.ndbs.file.persistent.data.convert.StringToPathStrategyConverter;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * FileControllerTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-17
 */
@DisplayName("FileControllerTest")
@WebMvcTest(FileController.class)
class FileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${app.rest.api.prefix}")
    private String apiPrefix;

    @Value("${app.rest.api.latest-version}")
    private String apiVersion;

    @MockBean
    private GetFileService getFileService;

    @MockBean
    private CreateFileService createFileService;

    @MockBean
    private DeleteFileService deleteFileService;

    @MockBean
    private FileSystemIdToStringConverter fileSystemIdToStringConverter;

    @MockBean
    private PathStrategyToStringConverter pathStrategyToStringConverter;

    @MockBean
    private StringToFileSystemIdConverter stringToFileSystemIdConverter;

    @MockBean
    private StringToPathStrategyConverter stringToPathStrategyConverter;

    @DisplayName("Should get a file with 200 status code when it exists")
    @Test
    void shouldGetFileWith200StatusCodeWhenItExists() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files/{fileId}", apiPrefix, apiVersion);

        var fileId = UUID.randomUUID();
        var file = createFile();

        // when / then
        given(getFileService.getFile(fileId))
            .willReturn(file);

        mockMvc.perform(get(apiUri, fileId).accept("application/json; charset=utf-8"))
            .andExpect(status().isOk());
    }

    @DisplayName("Should not get any file with 400 status code when an invalid id was passed")
    @Test
    void shouldNotGetAnyFileWith400StatusCodeWhenInvalidIdWasPassed() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files/{fileId}", apiPrefix, apiVersion);
        var fileId = "invalid_file_id";

        // when / then
        mockMvc.perform(get(apiUri, fileId).accept("application/json; charset=utf-8"))
            .andExpect(status().isBadRequest());

        verify(getFileService, never())
            .getFile(any(UUID.class));
    }

    @DisplayName("Should not get any file with 404 status code when it does not exist")
    @Test
    void shouldNotGetAnyFileWith404StatusCodeWhenItDoesNotExist() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files/{fileId}", apiPrefix, apiVersion);
        var fileId = UUID.randomUUID();

        // when / then
        given(getFileService.getFile(fileId))
            .willThrow(new FileNotFoundException("File was not found"));

        mockMvc.perform(get(apiUri, fileId).accept("application/json; charset=utf-8"))
            .andExpect(status().isNotFound());
    }

    @DisplayName("Should create a file with 201 status code when multipart file was successfully uploaded")
    @Test
    void shouldCreateFileWith201StatusCodeWhenMultipartFileWasSuccessfullyUploaded() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files", apiPrefix, apiVersion);
        var file = createFile();

        var imageFile = openImageFile();

        try (var imageInputStream = new FileInputStream(imageFile)) {
            var multipartFile = new MockMultipartFile("file", "funny_cat.jpg", "image/jpg", imageInputStream);

            // when / then
            given(createFileService.create(multipartFile))
                .willReturn(file);

            mockMvc.perform(multipart(apiUri).file(multipartFile))
                .andExpect(status().isCreated());
        }
    }

    @DisplayName("Should not create any file with 400 status code when file was not passed")
    @Test
    void shouldNotCreateAnyFileWith400StatusCodeWhenFileWasNotPassed() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files", apiPrefix, apiVersion);

        // when / then
        mockMvc.perform(multipart(apiUri))
            .andExpect(status().isBadRequest());

        verify(createFileService, never())
            .create(any(MultipartFile.class));
    }

    @DisplayName("Should not create any file with 503 status code when unable to save it")
    @Test
    void shouldNotCreateAnyFileWith503StatusCodeWhenUnableToSaveIt() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files", apiPrefix, apiVersion);
        var imageFile = openImageFile();

        try (var imageInputStream = new FileInputStream(imageFile)) {
            var multipartFile = new MockMultipartFile("file", "funny_cat.jpg", "image/jpg", imageInputStream);

            // when / then
            given(createFileService.create(multipartFile))
                .willThrow(new FileException("Something was wrong"));

            mockMvc.perform(multipart(apiUri).file(multipartFile))
                .andExpect(status().isServiceUnavailable());
        }
    }

    @DisplayName("Should delete a file with 204 status code when it exists")
    @Test
    void shouldDeleteFileWith204StatusCodeWhenItExists() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files/{fileId}", apiPrefix, apiVersion);

        var fileId = UUID.randomUUID();
        var file = createFile();

        // when / then
        given(getFileService.getFile(fileId))
            .willReturn(file);

        doNothing()
            .when(deleteFileService)
            .delete(file);

        mockMvc.perform(delete(apiUri, fileId).accept("application/json; charset=utf-8"))
            .andExpect(status().isNoContent());
    }

    @DisplayName("Should not delete any file with 400 status code when an invalid id was passed")
    @Test
    void shouldNotDeleteAnyFileWith400StatusCodeWhenInvalidIdWasPassed() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files/{fileId}", apiPrefix, apiVersion);
        var fileId = "invalid_file_id";

        // when / then
        mockMvc.perform(delete(apiUri, fileId).accept("application/json; charset=utf-8"))
            .andExpect(status().isBadRequest());

        verify(getFileService, never())
            .getFile(any(UUID.class));

        verify(deleteFileService, never())
            .delete(any(File.class));
    }

    @DisplayName("Should not delete any file with 404 status code when file was not found by passed id")
    @Test
    void shouldNotDeleteAnyFileWith400StatusCodeWhenFileWasNotFoundByPassedId() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files/{fileId}", apiPrefix, apiVersion);
        var fileId = UUID.randomUUID();

        // when / then
        given(getFileService.getFile(fileId))
            .willThrow(new FileNotFoundException("File was not found"));

        mockMvc.perform(delete(apiUri, fileId).accept("application/json; charset=utf-8"))
            .andExpect(status().isNotFound());

        verify(deleteFileService, never())
            .delete(any(File.class));
    }

    @DisplayName("Should not delete any file with 503 status code when something was wrong with filesystem")
    @Test
    void shouldNotDeleteAnyFileWith503StatusCodeWhenSomethingWasWrongWithFileSystem() throws Exception {
        // given
        var apiUri = String.format("/%s/%s/files/{fileId}", apiPrefix, apiVersion);
        var fileId = UUID.randomUUID();

        // when / then
        given(getFileService.getFile(fileId))
            .willThrow(new FileException("Something was wrong with filesystem"));

        mockMvc.perform(delete(apiUri, fileId).accept("application/json; charset=utf-8"))
            .andExpect(status().isServiceUnavailable());
    }

    private File createFile() {
        var fileFileSystemId = FileSystemId.create("local");
        var filePathStrategy = DefaultPathStrategy.create();

        return File.create(fileFileSystemId, filePathStrategy, "image/jpg", "a.mp4", 8500);
    }

    private java.io.File openImageFile() {
        var imagePathString = getClass().getClassLoader().getResource("data/funny_cat.jpg").getFile();
        return new java.io.File(imagePathString);
    }
}
