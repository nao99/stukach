package org.ndbs.filesystem.domain.filesystem;

import org.ndbs.filesystem.domain.filesystem.model.FileSystem;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemResource;

import java.io.IOException;

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
    public void copy(FileSystemResource source, FileSystemResource destination) throws FileSystemException {
        try (var sourceContent = fileSystem.read(source.getPath())) {
            fileSystem.write(destination.getPath(), sourceContent);
        } catch (IOException e) {
            throw new FileSystemIOException(e.getMessage(), e);
        }
    }

    @Override
    public void move(FileSystemResource source, FileSystemResource destination) throws FileSystemException {
        copy(source, destination);
        fileSystem.delete(source.getPath());
    }

    @Override
    public void delete(FileSystemResource source) throws FileSystemException {
        fileSystem.delete(source.getPath());
    }
}
