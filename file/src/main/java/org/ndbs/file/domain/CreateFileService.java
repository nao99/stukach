package org.ndbs.file.domain;

import org.ndbs.file.domain.model.File;
import org.springframework.web.multipart.MultipartFile;

/**
 * CreateFileService interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
public interface CreateFileService {
    /**
     * Creates a {@link File} from {@link MultipartFile}
     *
     * @param source an uploaded multipart file
     *
     * @return a newly created file
     * @throws FileException if something was wrong
     */
    File create(MultipartFile source) throws FileException;
}
