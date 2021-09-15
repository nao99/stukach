package org.ndbs.filesystem.domain.filesystem.model;

import java.nio.file.spi.FileSystemProvider;
import java.util.Objects;

/**
 * FileSystemId class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-12
 */
public class FileSystemId {
    private static final String LOCAL_ID     = "local";
    private static final String LOCAL_FIRST  = "/";
    private static final String LOCAL_SCHEME = "file";

    private final String id;

    /**
     * A path string or initial part of the path string
     */
    private final String first;

    /**
     * This field could be used to find a FileSystem on your machine <br>
     * In fact, scheme - is a URI scheme of your FileSystem <br>
     *
     * @see FileSystemProvider#getScheme() of your FileSystem
     * E.g. for Amazon S3 FileSystem the scheme is "s3"
     */
    private final String scheme;

    private FileSystemId(String id, String first, String scheme) {
        if (id == null) {
            throw new IllegalArgumentException("Filesystem id should not be null");
        }

        if (first == null) {
            throw new IllegalArgumentException("Filesystem first should not be null");
        }

        if (scheme == null) {
            throw new IllegalArgumentException("Filesystem scheme should not be null");
        }

        this.id = id;
        this.first = first;
        this.scheme = scheme;
    }

    public static FileSystemId create(String id, String first, String scheme) {
        return new FileSystemId(id, first, scheme);
    }

    public static FileSystemId create(String id, String scheme) {
        return new FileSystemId(id, LOCAL_FIRST, scheme);
    }

    public static FileSystemId create(String id) {
        return new FileSystemId(id, LOCAL_FIRST, LOCAL_SCHEME);
    }

    public static FileSystemId createLocal() {
        return new FileSystemId(LOCAL_ID, LOCAL_FIRST, LOCAL_SCHEME);
    }

    public String getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getScheme() {
        return scheme;
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

        return Objects.equals(fileSystemIdOther.id, id)
            && Objects.equals(fileSystemIdOther.first, first)
            && Objects.equals(fileSystemIdOther.scheme, scheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first, scheme);
    }

    @Override
    public String toString() {
        return "FileSystemId{" +
            "id='" + id + '\'' +
            ", first='" + first + '\'' +
            ", scheme='" + scheme + '\'' +
            '}';
    }
}
