package org.ndbs.filesystem.domain.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * FileSystemServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
public class FileSystemServiceImpl implements FileSystemService {
    private FileSystemServiceImpl() {}

    public static FileSystemServiceImpl create() {
        return new FileSystemServiceImpl();
    }

    @Override
    public void copy(Path filePath, Path destinationPath) throws FileSystemException {
        if (filePath.equals(destinationPath)) {
            return;
        }

        if (!Files.exists(filePath)) {
            throw new FileSystemResourceNotFoundException(String.format("File \"%s\" does not exist", filePath));
        }

        if (Files.isDirectory(filePath)) {
            throw new FileSystemResourceIsNotFileException(String.format("Resource \"%s\" is not a file", filePath));
        }

        if (Files.isDirectory(destinationPath)) {
            var exceptionMessage = String.format("Resource \"%s\" is not a file", destinationPath);
            throw new FileSystemResourceIsNotFileException(exceptionMessage);
        }

        if (Files.exists(destinationPath)) {
            throw new FileSystemResourceExistsException(String.format("File \"%s\" already exists", destinationPath));
        }

        try (var resourceContent = read(filePath)) {
            createParentDirectoriesIfNecessary(destinationPath);
            write(destinationPath, resourceContent);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void copy(InputStream fileStream, Path destinationPath) throws FileSystemException {
        if (Files.isDirectory(destinationPath)) {
            var exceptionMessage = String.format("Resource \"%s\" is not a file", destinationPath);
            throw new FileSystemResourceIsNotFileException(exceptionMessage);
        }

        if (Files.exists(destinationPath)) {
            throw new FileSystemResourceExistsException(String.format("File \"%s\" already exists", destinationPath));
        }

        createParentDirectoriesIfNecessary(destinationPath);
        write(destinationPath, fileStream);
    }

    @Override
    public void rename(Path oldResourcePath, Path newResourcePath) throws FileSystemException {
        if (oldResourcePath.equals(newResourcePath)) {
            return;
        }

        if (!Files.exists(oldResourcePath)) {
            var exceptionMessage = String.format("Resource \"%s\" does not exist", oldResourcePath);
            throw new FileSystemResourceNotFoundException(exceptionMessage);
        }

        if (Files.exists(newResourcePath)) {
            var exceptionMessage = String.format("Resource \"%s\" already exists", newResourcePath);
            throw new FileSystemResourceExistsException(exceptionMessage);
        }

        var oldResourceParentPath = oldResourcePath.getParent();
        var newResourceParentPath = newResourcePath.getParent();

        if (!oldResourceParentPath.equals(newResourceParentPath)) {
            throw new FileSystemResourceParentPathsAreDifferentException("Resource parent paths are different");
        }

        try {
            Files.move(oldResourcePath, newResourcePath);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(Path resourcePath) throws FileSystemException {
        if (!Files.exists(resourcePath)) {
            var exceptionMessage = String.format("Resource \"%s\" does not exist", resourcePath);
            throw new FileSystemResourceNotFoundException(exceptionMessage);
        }

        try {
            Files.delete(resourcePath);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void move(Path filePath, Path destinationPath) throws FileSystemException {
        copy(filePath, destinationPath);
        delete(filePath);
    }

    private InputStream read(Path filePath) throws FileSystemIOException {
        try {
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    private void write(Path filePath, InputStream fileContent) throws FileSystemIOException {
        try {
            Files.copy(fileContent, filePath);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    private void createParentDirectoriesIfNecessary(Path resource) throws FileSystemIOException {
        var parentDirectoriesPath = resource.getParent();
        if (Files.exists(parentDirectoriesPath)) {
            return;
        }

        try {
            Files.createDirectories(parentDirectoriesPath);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }
}
