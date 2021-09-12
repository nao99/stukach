package org.ndbs.filesystem.domain.filesystem.model;

import java.util.Objects;

/**
 * FileSystemId class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
public class FileSystemId {
    public static final String LOCAL = "local";

    private final String id;

    private FileSystemId(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Filesystem id should not be null");
        }

        this.id = id;
    }

    public static FileSystemId create(String id) {
        return new FileSystemId(id);
    }

    public static FileSystemId local() {
        return new FileSystemId(LOCAL);
    }

    public String getId() {
        return id;
    }

    public boolean isLocal() {
        return id.equals(LOCAL);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        var fileSystemIdOther = (FileSystemId) other;

        return Objects.equals(fileSystemIdOther.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
