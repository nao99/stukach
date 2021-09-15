package org.ndbs.filesystem.domain.filesystem;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * FileSystemService interface <br>
 *
 * Below are the following notation: <br>
 *  - Resource     -> any directory or file <br>
 *  - Regular file -> any file (not simlink, directory or something else) <br>
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-15
 */
public interface FileSystemService {
    /**
     * Creates a copy of regular file
     *
     * @param filePath        a regular file path
     * @param destinationPath a destination path
     *
     * @throws FileSystemResourceNotFoundException if the file is not found
     * @throws FileSystemResourceIsNotFileException if by passed file path was found a not file
     * @throws FileSystemResourceExistsException if a file already exists at the destination path
     * @throws FileSystemIOException if something was wrong while copying
     * @throws FileSystemException if something was wrong
     */
    void copy(Path filePath, Path destinationPath) throws FileSystemException;

    /**
     * Creates a copy of regular file
     *
     * @param fileStream      a file stream
     * @param destinationPath a destination path
     *
     * @throws FileSystemResourceExistsException if a file already exists at the destination path
     * @throws FileSystemIOException if something was wrong while copying
     * @throws FileSystemException if something was wrong
     */
    void copy(InputStream fileStream, Path destinationPath) throws FileSystemException;

    /**
     * Renames a resource
     *
     * @param oldResourcePath an old resource path
     * @param newResourcePath a new resource path
     *
     * @throws FileSystemResourceNotFoundException if the old resource is not found by path
     * @throws FileSystemResourceExistsException if a resource already exists by the new resource path
     * @throws FileSystemIOException if something was wrong while renaming
     * @throws FileSystemException if something was wrong
     */
    void rename(Path oldResourcePath, Path newResourcePath) throws FileSystemException;

    /**
     * Deletes a resource
     *
     * @param resourcePath a resource path
     *
     * @throws FileSystemResourceNotFoundException if the resource is not found by path
     * @throws FileSystemIOException if something was wrong while deleting
     * @throws FileSystemException if something was wrong
     */
    void delete(Path resourcePath) throws FileSystemException;

    /**
     * Moves a regular file
     *
     * @param filePath        a regular file path
     * @param destinationPath a destination path
     *
     * @throws FileSystemResourceNotFoundException if the file is not found
     * @throws FileSystemResourceExistsException if a file already exists at the destination path
     * @throws FileSystemResourceIsNotFileException if by passed file path was found a not file
     * @throws FileSystemIOException if something was wrong while moving
     * @throws FileSystemException if something was wrong
     */
    void move(Path filePath, Path destinationPath) throws FileSystemException;
}
