package org.ndbs.filesystem.domain;

import org.ndbs.filesystem.domain.model.FilesystemResource;

import java.net.URL;

/**
 * FilesystemService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-08-04
 */
public interface FilesystemService {
    /**
     * Copies a {@link FilesystemResource} from one place to another
     *
     * @param source      a source resource
     * @param destination a destination resource
     *
     * @throws FilesystemException if something was wrong
     */
    void copy(FilesystemResource source, FilesystemResource destination) throws FilesystemException;

    /**
     * Moves a {@link FilesystemResource} from one place to another
     *
     * @param source      a source resource
     * @param destination a destination resource
     *
     * @throws FilesystemException if something was wrong
     */
    void move(FilesystemResource source, FilesystemResource destination) throws FilesystemException;

    /**
     * Deletes a {@link FilesystemResource}
     *
     * @param source a source resource
     * @throws FilesystemException if something was wrong
     */
    void delete(FilesystemResource source) throws FilesystemException;

    /**
     * Gets a public {@link URL} for a resource
     *
     * @param source a source resource
     *
     * @return a public resource's url
     * @throws FilesystemException if something was wrong
     */
    URL getUrl(FilesystemResource source) throws FilesystemException;
}
