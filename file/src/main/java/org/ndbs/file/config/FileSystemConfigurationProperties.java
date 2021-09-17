package org.ndbs.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.net.URI;
import java.util.List;

/**
 * FileSystemConfigurationProperties class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-15
 */
@ConfigurationProperties("app.filesystem")
@ConstructorBinding
public class FileSystemConfigurationProperties {
    @NestedConfigurationProperty
    private final List<FileSystemInfo> filesystems;

    public FileSystemConfigurationProperties(List<FileSystemInfo> filesystems) {
        this.filesystems = filesystems;
    }

    @ConstructorBinding
    public static class FileSystemInfo {
        private final String id;
        private final URI uri;

        public FileSystemInfo(String id, URI uri) {
            this.id = id;
            this.uri = uri;
        }

        public String getId() {
            return id;
        }

        public URI getUri() {
            return uri;
        }
    }

    public List<FileSystemInfo> getFilesystemsInfo() {
        return filesystems;
    }
}
