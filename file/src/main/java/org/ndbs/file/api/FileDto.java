package org.ndbs.file.api;

import org.ndbs.file.domain.model.File;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * FileDto class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-17
 */
public class FileDto {
    private final File file;

    private FileDto(File file) {
        this.file = file;
    }

    public static FileDto create(File file) {
        return new FileDto(file);
    }

    public UUID getId() {
        return file.getId();
    }

    public String getMimeType() {
        return file.getMimeType();
    }

    public String getBasename() {
        return file.getName();
    }

    public long getFilesize() {
        return file.getFilesize();
    }

    public boolean isConfirmed() {
        return file.isConfirmed();
    }

    public boolean isDeleted() {
        return file.isDeleted();
    }

    public LocalDateTime getCreatedAt() {
        return file.getCreatedAt();
    }

    public LocalDateTime getUpdatedAt() {
        return file.getUpdatedAt();
    }
}
