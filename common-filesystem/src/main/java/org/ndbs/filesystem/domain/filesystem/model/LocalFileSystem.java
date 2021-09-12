package org.ndbs.filesystem.domain.filesystem.model;

import org.ndbs.filesystem.domain.filesystem.FileSystemException;

import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * FileSystem class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
public class LocalFileSystem implements FileSystem {
    private final FileSystemId fileSystemId;
    private final java.nio.file.FileSystem fileSystem;

    private LocalFileSystem(FileSystemId fileSystemId, java.nio.file.FileSystem fileSystem) {
        if (fileSystemId == null) {
            throw new IllegalArgumentException("Filesystem id should not be null");
        }

        if (fileSystem == null) {
            throw new IllegalArgumentException("Filesystem should not be null");
        }

        this.fileSystemId = fileSystemId;
        this.fileSystem = fileSystem;
    }

    public static LocalFileSystem create() {
        var fileSystemId = FileSystemId.local();
        var fileSystem = FileSystems.getDefault();

        return new LocalFileSystem(fileSystemId, fileSystem);
    }

    @Override
    public FileSystemId getFileSystemId() {
        return fileSystemId;
    }

    @Override
    public boolean has(Path path) throws FileSystemException {
        return false;
    }

    @Override
    public InputStream read(Path path) throws FileSystemException {
        return null;
    }

    @Override
    public void write(Path path, InputStream fileContent) throws FileSystemException {

    }

    @Override
    public void rename(Path oldPath, Path newPath) throws FileSystemException {

    }

    @Override
    public void copy(Path oldPath, Path newPath) throws FileSystemException {

    }

    @Override
    public void delete(Path path) throws FileSystemException {

    }

    @Override
    public long getSize(Path path) throws FileSystemException {
        return 0;
    }
}
