package org.ndbs.file.domain.model;

import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.domain.path.model.PathStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * File class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
@Table("files")
public class File implements org.ndbs.filesystem.domain.filesystem.model.File {
    @Id
    private final UUID id;

    @Column("filesystem_id")
    private final FileSystemId filesystemId;
    private final PathStrategy pathStrategy;
    private final String mimeType;
    private final String basename;
    private final long filesize;
    private boolean confirmed;
    private boolean deleted;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private File(
        @Nullable UUID id,
        FileSystemId filesystemId,
        PathStrategy pathStrategy,
        String mimeType,
        String basename,
        long filesize,
        boolean confirmed,
        boolean deleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.filesystemId = filesystemId;
        this.pathStrategy = pathStrategy;
        this.mimeType = mimeType;
        this.basename = basename;
        this.filesize = filesize;
        this.confirmed = confirmed;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static File create(
        FileSystemId filesystemId,
        PathStrategy pathStrategy,
        String mimeType,
        String basename,
        long filesize
    ) {
        var currentDateTime = LocalDateTime.now();
        return new File(
            null,
            filesystemId,
            pathStrategy,
            mimeType,
            basename,
            filesize,
            false,
            false,
            currentDateTime,
            currentDateTime
        );
    }

    File withId(UUID id) {
        return new File(
            id,
            filesystemId,
            pathStrategy,
            mimeType,
            basename,
            filesize,
            confirmed,
            deleted,
            createdAt,
            updatedAt
        );
    }

    public UUID getId() {
        return id;
    }

    public FileSystemId getFilesystemId() {
        return filesystemId;
    }

    public PathStrategy getPathStrategy() {
        return pathStrategy;
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String getName() {
        return basename;
    }

    public long getFilesize() {
        return filesize;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void confirm() {
        confirmed = true;
        updateTimestamp();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        deleted = true;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void updateTimestamp() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "File{" +
            "id=" + id +
            ", filesystemId=" + filesystemId +
            ", pathStrategy=" + pathStrategy +
            ", mimeType='" + mimeType + '\'' +
            ", basename='" + basename + '\'' +
            ", filesize=" + filesize +
            ", confirmed=" + confirmed +
            ", deleted=" + deleted +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
