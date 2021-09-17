package org.ndbs.file.domain;

import org.ndbs.file.domain.model.File;
import org.ndbs.file.persistent.api.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * GetFileServiceImpl class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Service
public class GetFileServiceImpl implements GetFileService {
    private final FileRepository repository;

    @Autowired
    public GetFileServiceImpl(FileRepository repository) {
        this.repository = repository;
    }

    @Override
    public File getFile(UUID id) throws FileNotFoundException {
        return repository.findById(id)
            .orElseThrow(() -> new FileNotFoundException(String.format("File \"%s\" was not found", id)));
    }
}
