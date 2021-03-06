package org.ndbs.filesystem.registry;

import org.ndbs.filesystem.domain.filesystem.FileSystemNotFoundException;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;

import java.nio.file.FileSystem;
import java.util.HashMap;
import java.util.Map;

/**
 * FileSystemRegistry class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
public class FileSystemRegistry {
    private final Map<FileSystemId, FileSystem> fileSystems;

    private FileSystemRegistry() {
        this.fileSystems = new HashMap<>();
    }

    public static FileSystemRegistry create() {
        return new FileSystemRegistry();
    }

    public boolean hasFileSystem(FileSystemId fileSystemId) {
        return fileSystems.containsKey(fileSystemId);
    }

    public void addFileSystem(FileSystemId fileSystemId, FileSystem fileSystem) {
        if (hasFileSystem(fileSystemId)) {
            return;
        }

        fileSystems.put(fileSystemId, fileSystem);
    }

    public FileSystem getFileSystem(FileSystemId fileSystemId) throws FileSystemNotFoundException {
        if (!hasFileSystem(fileSystemId)) {
            throw new FileSystemNotFoundException(String.format("Filesystem \"%s\" is not registered", fileSystemId));
        }

        return fileSystems.get(fileSystemId);
    }

    public boolean empty() {
        return fileSystems.isEmpty();
    }
}
