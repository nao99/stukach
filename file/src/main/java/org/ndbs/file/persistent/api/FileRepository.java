package org.ndbs.file.persistent.api;

import org.ndbs.file.domain.model.File;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * FileRepository interface
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
public interface FileRepository extends CrudRepository<File, UUID> {
}
