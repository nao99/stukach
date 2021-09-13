package org.ndbs.filesystem.domain.filesystem.model;

import org.ndbs.filesystem.domain.filesystem.FileSystemException;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * FileSystem interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
public interface FileSystem {
    /**
     * Checks if a file exists by {@link Path}
     *
     * @param path a file path
     *
     * @return a file existence status
     * @throws FileSystemException if something was wrong
     */
    boolean has(Path path) throws FileSystemException;

    /**
     * Reads a file by {@link Path}
     *
     * @param path a file path
     *
     * @return a file input stream
     * @throws FileSystemException if something was wrong
     */
    InputStream read(Path path) throws FileSystemException;

    /**
     * Writes a file content by {@link Path}
     *
     * @param path        a file path
     * @param fileContent a file content input stream
     *
     * @throws FileSystemException if something was wrong
     */
    void write(Path path, InputStream fileContent) throws FileSystemException;

    /**
     * Renames a file by {@link Path}
     *
     * @param oldPath a file old path
     * @param newPath a file new path
     *
     * @throws FileSystemException if something was wrong
     */
    void rename(Path oldPath, Path newPath) throws FileSystemException;

    /**
     * Copies a file by {@link Path}
     *
     * @param oldPath a file old path
     * @param newPath a file new path
     *
     * @throws FileSystemException if something was wrong
     */
    void copy(Path oldPath, Path newPath) throws FileSystemException;

    /**
     * Deletes a file by {@link Path}
     *
     * @param path a file path
     *
     * @throws FileSystemException if something was wrong
     */
    void delete(Path path) throws FileSystemException;

    /**
     * Gets a filesize by {@link Path}
     *
     * @param path a file path
     *
     * @return a filesize
     * @throws FileSystemException if something was wrong
     */
    long getSize(Path path) throws FileSystemException;
}
