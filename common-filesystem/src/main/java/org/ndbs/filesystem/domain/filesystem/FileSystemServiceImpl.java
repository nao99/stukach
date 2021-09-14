package org.ndbs.filesystem.domain.filesystem;

import org.ndbs.filesystem.domain.filesystem.model.FileSystem;

import java.io.IOException;
import java.nio.file.Path;

/**
 * FileSystemServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
public class FileSystemServiceImpl implements FileSystemService {
    private final FileSystem fileSystem;

    private FileSystemServiceImpl(FileSystem fileSystem) {
        if (fileSystem == null) {
            throw new IllegalArgumentException("Filesystem should not be null");
        }

        this.fileSystem = fileSystem;
    }

    public static FileSystemServiceImpl create(FileSystem fileSystem) {
        return new FileSystemServiceImpl(fileSystem);
    }

    @Override
    public void copy(Path source, Path destination) throws FileSystemException {
        try (var sourceContent = fileSystem.read(source)) {
            fileSystem.write(destination, sourceContent);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void move(Path source, Path destination) throws FileSystemException {
        copy(source, destination);
        fileSystem.delete(source);
    }

    @Override
    public void delete(Path source) throws FileSystemException {
        fileSystem.delete(source);
    }
}
