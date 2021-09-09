package org.ndbs.filesystem.domain.model;

import org.apache.commons.io.FilenameUtils;
import org.ndbs.filesystem.domain.FilesystemResourceException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

/**
 * FilesystemResource class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
public class FilesystemResource {
    private final Filesystem filesystem;
    private final String absolutePath;

    private FilesystemResource(Filesystem filesystem, String absolutePath) {
        this.filesystem = filesystem;
        this.absolutePath = absolutePath;
    }

    public static FilesystemResource create(Filesystem filesystem, String absolutePath) {
        return new FilesystemResource(filesystem, absolutePath);
    }

    public static FilesystemResource asRoot(String absolutePath) {
        return new FilesystemResource(Filesystem.LOCAL, absolutePath);
    }

    public long getFilesize() throws FilesystemResourceException {
        try {
            return Files.size(new File(absolutePath).toPath());
        } catch (IOException e) {
            throw new FilesystemResourceException(String.format("File \"%s\" was not found", absolutePath), e);
        }
    }

    public String getMimeType() throws FilesystemResourceException {
        try {
            return Files.probeContentType(new File(absolutePath).toPath());
        } catch (IOException e) {
            throw new FilesystemResourceException(String.format("File \"%s\" was not found", absolutePath), e);
        }
    }

    public String getName() {
        return FilenameUtils.getName(absolutePath);
    }

    public String getExtension() {
        return FilenameUtils.getExtension(absolutePath);
    }

    public Filesystem getFilesystem() {
        return filesystem;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public boolean isLocal() {
        return filesystem == Filesystem.LOCAL;
    }

    // TODO: Rewrite equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilesystemResource that = (FilesystemResource) o;
        return filesystem == that.filesystem && Objects.equals(absolutePath, that.absolutePath);
    }
}
