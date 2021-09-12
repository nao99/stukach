package org.ndbs.filesystem.domain.filesystem.model;

import org.apache.commons.io.FilenameUtils;
import org.ndbs.filesystem.domain.filesystem.FileSystemResourceException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * FileSystemResource class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
public class FileSystemResource {
    private final FileSystemId fileSystemId;
    private final Path path;

    private FileSystemResource(FileSystemId fileSystemId, Path path) {
        if (fileSystemId == null) {
            throw new IllegalArgumentException("Filesystem id should not be null");
        }

        if (path == null) {
            throw new IllegalArgumentException("Path should not be null");
        }

        this.fileSystemId = fileSystemId;
        this.path = path;
    }

    public static FileSystemResource create(FileSystemId fileSystemId, Path path) {
        return new FileSystemResource(fileSystemId, path);
    }

    public static FileSystemResource fromRoot(Path path) {
        var fileSystemId = FileSystemId.local();
        return new FileSystemResource(fileSystemId, path);
    }

    public static FileSystemResource fromRoot(String path) {
        return fromRoot(Path.of(path));
    }

    public FileSystemId getFileSystemId() {
        return fileSystemId;
    }

    public Path getPath() {
        return path;
    }

    public boolean isLocal() {
        return fileSystemId.isLocal();
    }

    public String getMimeType() throws FileSystemResourceException {
        try {
            return Files.probeContentType(path);
        } catch (IOException e) {
            throw new FileSystemResourceException(String.format("File \"%s\" was not found", path), e);
        }
    }

    public String getFilename() {
        return path.getFileName().toString();
    }

    public String getExtension() {
        var filename = getFilename();
        var filenameIndexOfExtension = FilenameUtils.indexOfExtension(filename);

        return filename.substring(filenameIndexOfExtension + 1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        var filesystemResourceOther = (FileSystemResource) other;

        return Objects.equals(filesystemResourceOther.fileSystemId, fileSystemId)
            && Objects.equals(filesystemResourceOther.path, path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileSystemId, path);
    }
}
