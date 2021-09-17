package org.ndbs.file.domain;

import org.ndbs.file.domain.model.File;
import org.ndbs.filesystem.domain.path.PathStrategyException;
import org.ndbs.filesystem.registry.FileSystemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/**
 * BuildFilePathServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-15
 */
@Service
public class BuildFilePathServiceImpl implements BuildFilePathService {
    private static final Logger logger = LoggerFactory.getLogger(BuildFilePathServiceImpl.class);

    private final FileSystemRegistry fileSystemRegistry;

    @Autowired
    public BuildFilePathServiceImpl(FileSystemRegistry fileSystemRegistry) {
        this.fileSystemRegistry = fileSystemRegistry;
    }

    @Override
    public Path build(File file) throws FileException {
        var filesystemId = file.getFilesystemId();
        var pathStrategy = file.getPathStrategy();

        try {
            var fileSystem = fileSystemRegistry.getFileSystem(filesystemId);
            var fileSystemSeparator = fileSystem.getSeparator();

            var first = String.format("%s%s", fileSystemSeparator, filesystemId);
            var path = pathStrategy.buildPath(file);

            return fileSystem.getPath(first, path);
        } catch (org.ndbs.filesystem.domain.filesystem.FileSystemNotFoundException e) {
            var exceptionMessage = String.format("Filesystem \"%s\" was not found", filesystemId);
            logger.error(exceptionMessage);

            throw new FileSystemNotFoundException(exceptionMessage, e);
        } catch (PathStrategyException e) {
            throw new FilePathBuildingException(e.getMessage(), e);
        }
    }
}
