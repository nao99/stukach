package org.ndbs.file.domain;

import org.ndbs.file.domain.model.File;

import java.nio.file.Path;

/**
 * BuildFilePathService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-15
 */
public interface BuildFilePathService {
    /**
     * Builds a {@link Path} to a {@link File}
     *
     * @param file a file
     *
     * @return a file path
     *
     * @throws FileSystemNotFoundException if file's filesystem was not found
     * @throws FilePathBuildingException if unable to build a path to the file
     * @throws FileException if something was wrong
     */
    Path build(File file) throws FileException;
}
