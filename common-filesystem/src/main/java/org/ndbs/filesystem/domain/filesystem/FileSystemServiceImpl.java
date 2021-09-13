package org.ndbs.filesystem.domain.filesystem;

import org.ndbs.filesystem.domain.filesystem.model.FileSystem;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemResource;
import org.ndbs.filesystem.registry.FileSystemRegistry;

import java.io.IOException;

/**
 * FileSystemServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
public class FileSystemServiceImpl implements FileSystemService {
    private final FileSystemRegistry registry;

    public FileSystemServiceImpl(FileSystemRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void copy(FileSystemResource source, FileSystemResource destination) throws FileSystemException {
        var sourceFileSystem = getResourceFileSystem(source);
        var destinationFileSystem = getResourceFileSystem(destination);

        if (fileSystemsEqual(sourceFileSystem, destinationFileSystem)) {
            sourceFileSystem.copy(source.getPath(), destination.getPath());
            return;
        }

        try (var sourceContent = sourceFileSystem.read(source.getPath())) {
            destinationFileSystem.write(destination.getPath(), sourceContent);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void move(FileSystemResource source, FileSystemResource destination) throws FileSystemException {
        var sourceFileSystem = getResourceFileSystem(source);
        var destinationFileSystem = getResourceFileSystem(destination);

        if (fileSystemsEqual(sourceFileSystem, destinationFileSystem)) {
            sourceFileSystem.rename(source.getPath(), destination.getPath());
            return;
        }

        copy(source, destination);
        sourceFileSystem.delete(source.getPath());
    }

    @Override
    public void delete(FileSystemResource source) throws FileSystemException {
        var sourceFileSystem = getResourceFileSystem(source);
        sourceFileSystem.delete(source.getPath());
    }

    private FileSystem getFileSystem(FileSystemId fileSystemId) {
        return registry.getFileSystem(fileSystemId);
    }

    private FileSystem getResourceFileSystem(FileSystemResource fileSystemResource) {
        return getFileSystem(fileSystemResource.getFileSystemId());
    }

    private boolean fileSystemsEqual(FileSystem fileSystem1, FileSystem fileSystem2) {
        var fileSystem1Id = fileSystem1.getFileSystemId();
        var fileSystem2Id = fileSystem2.getFileSystemId();

        return fileSystem1Id.equals(fileSystem2Id);
    }
}
