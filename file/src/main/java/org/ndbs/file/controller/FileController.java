package org.ndbs.file.controller;

import org.ndbs.file.api.FileDto;
import org.ndbs.file.domain.*;
import org.ndbs.file.domain.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * FileController class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-10
 */
@RestController
@RequestMapping("/${app.rest.api.prefix}/${app.rest.api.latest-version}/files")
public class FileController {
    private final GetFileService getFileService;
    private final CreateFileService createFileService;
    private final DeleteFileService deleteFileService;

    @Autowired
    public FileController(
        GetFileService getFileService,
        CreateFileService createFileService,
        DeleteFileService deleteFileService
    ) {
        this.getFileService = getFileService;
        this.createFileService = createFileService;
        this.deleteFileService = deleteFileService;
    }

    /**
     * API endpoint to get a {@link File} by id
     *
     * @param fileId a file id
     *
     * @return a file
     * @throws ResponseStatusException with status 404 if a file was not found by passed id
     */
    @GetMapping("/{fileId}")
    public ResponseEntity<FileDto> getFile(@PathVariable("fileId") UUID fileId) throws ResponseStatusException {
        try {
            var file = getFileService.getFile(fileId);
            var fileDto = FileDto.create(file);

            return ResponseEntity.ok(fileDto);
        } catch (FileNotFoundException e) {
            var exceptionMessage = String.format("File with id \"%s\" was not found", fileId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exceptionMessage, e);
        }
    }

    /**
     * API endpoint to create a {@link File}
     *
     * @param uploadedFile an uploaded file
     *
     * @return a newly created file
     * @throws ResponseStatusException with status 503 if unable to save file
     */
    @PostMapping
    public ResponseEntity<FileDto> createFile(@RequestParam("file") MultipartFile uploadedFile)
        throws ResponseStatusException {
        try {
            var file = createFileService.create(uploadedFile);
            var fileDto = FileDto.create(file);

            return ResponseEntity.status(HttpStatus.CREATED)
                .body(fileDto);
        } catch (FileException e) {
            var exceptionMessage = "Unable to create file now. Please try again later";
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, exceptionMessage, e);
        }
    }

    /**
     * API endpoint to delete a {@link File} by id
     *
     * @param fileId a file id
     *
     * @return nothing
     * @throws ResponseStatusException with status 404 if a file was not found by passed id
     */
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable("fileId") UUID fileId) throws ResponseStatusException {
        try {
            var file = getFileService.getFile(fileId);
            deleteFileService.delete(file);

            return ResponseEntity.noContent()
                .build();
        } catch (FileNotFoundException e) {
            var exceptionMessage = String.format("File with id \"%s\" was not found", fileId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exceptionMessage, e);
        } catch (FileException e) {
            var exceptionMessage = "Unable to delete file now. Please try again later";
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, exceptionMessage, e);
        }
    }
}
