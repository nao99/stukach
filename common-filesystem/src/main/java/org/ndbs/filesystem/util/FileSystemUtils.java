package org.ndbs.filesystem.util;

import org.ndbs.filesystem.domain.filesystem.FileSystemNotFoundException;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.ProviderNotFoundException;

/**
 * FileSystemUtils class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
public class FileSystemUtils {
    /**
     * Gets a {@link FileSystem} by {@link FileSystemId}
     *
     * @param fileSystemId a filesystem id
     * @return a filesystem
     */
    public static FileSystem getFileSystemBy(FileSystemId fileSystemId) {
        var fileSystemUri = URI.create(String.format("%s:///", fileSystemId.getScheme()));
        try {
            return FileSystems.getFileSystem(fileSystemUri);
        } catch (java.nio.file.FileSystemNotFoundException | ProviderNotFoundException e) {
            throw new FileSystemNotFoundException(e.getMessage(), e);
        }
    }
}
