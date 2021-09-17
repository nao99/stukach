package org.ndbs.file.domain;

import org.ndbs.file.domain.model.File;
import org.ndbs.file.persistent.api.FileRepository;
import org.ndbs.filesystem.domain.filesystem.FileSystemException;
import org.ndbs.filesystem.domain.filesystem.FileSystemService;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;
import org.ndbs.filesystem.domain.path.model.PathStrategy;
import org.ndbs.filesystem.registry.PathStrategyRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * CreateFileServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Service
public class CreateFileServiceImpl implements CreateFileService {
    private static final Logger logger = LoggerFactory.getLogger(CreateFileServiceImpl.class);

    private static final String PATH_STRATEGY_NAME = DefaultPathStrategy.NAME;

    private final FileRepository repository;
    private final FileSystemService fileSystemService;
    private final BuildFilePathService buildFilePathService;
    private final PathStrategyRegistry pathStrategyRegistry;
    private final String filesystemId;

    @Autowired
    public CreateFileServiceImpl(
        FileRepository repository,
        FileSystemService fileSystemService,
        BuildFilePathService buildFilePathService,
        PathStrategyRegistry pathStrategyRegistry,
        @Value("${app.aws.bucket}") String filesystemId
    ) {
        this.repository = repository;
        this.fileSystemService = fileSystemService;
        this.buildFilePathService = buildFilePathService;
        this.pathStrategyRegistry = pathStrategyRegistry;
        this.filesystemId = filesystemId;
    }

    @Override
    @Transactional
    public File create(MultipartFile source) throws FileException {
        var fileSystemId = FileSystemId.create(filesystemId);
        var pathStrategy = pathStrategyRegistry.getStrategy(PATH_STRATEGY_NAME);

        var file = createFileEntity(source, fileSystemId, pathStrategy);
        var destinationPath = buildFilePathService.build(file);

        try {
            fileSystemService.copy(source.getInputStream(), destinationPath);
        } catch (IOException e) {
            logger.error("Unable to get input stream from uploaded file: {}", e.getMessage());
            throw new FileException(e.getMessage(), e);
        } catch (FileSystemException e) {
            logger.error(e.getMessage());
            throw new FileException(e.getMessage(), e);
        }

        return file;
    }

    private File createFileEntity(MultipartFile source, FileSystemId fileSystemId, PathStrategy pathStrategy) {
        var basename = source.getOriginalFilename();
        var mimeType = source.getContentType();
        var filesize = source.getSize();

        var file = File.create(fileSystemId, pathStrategy, mimeType, basename, filesize);

        return repository.save(file);
    }
}
