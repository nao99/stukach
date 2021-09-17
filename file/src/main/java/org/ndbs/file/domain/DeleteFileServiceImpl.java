package org.ndbs.file.domain;

import org.ndbs.file.domain.model.File;
import org.ndbs.file.persistent.api.FileRepository;
import org.ndbs.filesystem.domain.filesystem.FileSystemIOException;
import org.ndbs.filesystem.domain.filesystem.FileSystemResourceNotFoundException;
import org.ndbs.filesystem.domain.filesystem.FileSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DeleteFileServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-17
 */
@Service
public class DeleteFileServiceImpl implements DeleteFileService {
    private static final Logger logger = LoggerFactory.getLogger(DeleteFileServiceImpl.class);

    private final FileRepository repository;
    private final FileSystemService fileSystemService;
    private final BuildFilePathService buildFilePathService;

    @Autowired
    public DeleteFileServiceImpl(
        FileRepository repository,
        FileSystemService fileSystemService,
        BuildFilePathService buildFilePathService
    ) {
        this.repository = repository;
        this.fileSystemService = fileSystemService;
        this.buildFilePathService = buildFilePathService;
    }

    @Override
    @Transactional
    public void delete(File file) throws FileException {
        var filePath = buildFilePathService.build(file);
        repository.delete(file);

        try {
            fileSystemService.delete(filePath);
        } catch (FileSystemResourceNotFoundException e) {
            logger.warn(e.getMessage());
        } catch (FileSystemIOException e) {
            logger.error(e.getMessage());
            throw new FileException(e.getMessage(), e);
        }
    }
}
