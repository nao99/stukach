package org.ndbs.file.config;

import org.ndbs.filesystem.domain.filesystem.FileSystemService;
import org.ndbs.filesystem.domain.filesystem.FileSystemServiceImpl;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.registry.FileSystemRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.nio.file.FileSystems;

/**
 * FileSystemConfiguration class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Configuration
@EnableConfigurationProperties({FileSystemConfigurationProperties.class})
public class FileSystemConfiguration {
    private final FileSystemConfigurationProperties fileSystemConfigurationProperties;

    @Autowired
    public FileSystemConfiguration(FileSystemConfigurationProperties fileSystemConfigurationProperties) {
        this.fileSystemConfigurationProperties = fileSystemConfigurationProperties;
    }

    @Bean
    public FileSystemService filesystemService() {
        return FileSystemServiceImpl.create();
    }

    @Bean
    @DependsOn("amazonS3FileSystem")
    public FileSystemRegistry fileSystemRegistry() {
        var registry = FileSystemRegistry.create();
        for (var fileSystemInfo : fileSystemConfigurationProperties.getFilesystemsInfo()) {
            var fileSystemId = FileSystemId.create(fileSystemInfo.getId());
            var fileSystemUri = fileSystemInfo.getUri();

            var fileSystem = FileSystems.getFileSystem(fileSystemUri);
            registry.addFileSystem(fileSystemId, fileSystem);
        }

        return registry;
    }
}
