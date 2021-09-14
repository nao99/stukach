package org.ndbs.file.config;

import org.ndbs.filesystem.domain.filesystem.FileSystemService;
import org.ndbs.filesystem.domain.filesystem.FileSystemServiceImpl;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileSystemConfiguration class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Configuration
public class FileSystemConfiguration {
    @Bean
    public FileSystemService filesystemService() {
        var fileSystem = FileSystemImpl.create();
        return FileSystemServiceImpl.create(fileSystem);
    }
}
