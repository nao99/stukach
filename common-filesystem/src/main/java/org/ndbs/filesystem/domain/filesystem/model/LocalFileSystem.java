package org.ndbs.filesystem.domain.filesystem.model;

import org.apache.commons.io.FileUtils;
import org.ndbs.filesystem.domain.filesystem.FileSystemException;
import org.ndbs.filesystem.domain.filesystem.FileSystemIOException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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

    private LocalFileSystem(FileSystemId fileSystemId) {
        if (fileSystemId == null) {
            throw new IllegalArgumentException("Filesystem id should not be null");
        }

        this.fileSystemId = fileSystemId;
    }

    public static LocalFileSystem create() {
        var fileSystemId = FileSystemId.local();
        return new LocalFileSystem(fileSystemId);
    }

    @Override
    public FileSystemId getFileSystemId() {
        return fileSystemId;
    }

    @Override
    public boolean has(Path path) throws FileSystemException {
        return Files.exists(path);
    }

    @Override
    public InputStream read(Path path) throws FileSystemException {
        try {
            var file = path.toFile();
            var fileInputStream = FileUtils.openInputStream(file);

            return new BufferedInputStream(fileInputStream);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void write(Path path, InputStream fileContent) throws FileSystemException {
        try {
            var fileToWrite = path.toFile();
            FileUtils.copyInputStreamToFile(fileContent, fileToWrite);
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
            var oldFile = oldPath.toFile();
            var newFile = newPath.toFile();

            FileUtils.copyFile(oldFile, newFile);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Path path) throws FileSystemException {
        var file = path.toFile();
        FileUtils.deleteQuietly(file);
    }

    @Override
    public long getSize(Path path) throws FileSystemException {
        try {
            var file = path.toFile();
            return FileUtils.sizeOf(file);
        } catch (IllegalArgumentException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }
}
