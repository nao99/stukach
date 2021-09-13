package org.ndbs.filesystem.domain.filesystem.model;

import org.ndbs.filesystem.domain.filesystem.FileSystemException;
import org.ndbs.filesystem.domain.filesystem.FileSystemIOException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * FileSystemImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
public class FileSystemImpl implements FileSystem {
    private final FileSystemId fileSystemId;

    private FileSystemImpl(FileSystemId fileSystemId) {
        if (fileSystemId == null) {
            throw new IllegalArgumentException("Filesystem id should not be null");
        }

        this.fileSystemId = fileSystemId;
    }

    public static FileSystemImpl create(FileSystemId fileSystemId) {
        return new FileSystemImpl(fileSystemId);
    }

    @Override
    public FileSystemId getFileSystemId() {
        return fileSystemId;
    }

    @Override
    public boolean has(Path path) {
        return Files.exists(path);
    }

    @Override
    public InputStream read(Path path) throws FileSystemException {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void write(Path path, InputStream fileContent) throws FileSystemException {
        try {
            Files.copy(fileContent, path);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void rename(Path oldPath, Path newPath) throws FileSystemException {
        if (oldPath.equals(newPath)) {
            return;
        }

        copy(oldPath, newPath);
        delete(oldPath);
    }

    @Override
    public void copy(Path oldPath, Path newPath) throws FileSystemException {
        if (oldPath.equals(newPath)) {
            return;
        }

        try {
            Files.copy(oldPath, newPath);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public long getSize(Path path) throws FileSystemException {
        try {
            return Files.size(path);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }
}
