package org.ndbs.file.persistent.data.convert;

import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * StringToFileSystemIdConverter enum
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Component
@ReadingConverter
public class StringToFileSystemIdConverter implements Converter<String, FileSystemId> {
    @Override
    @Nullable
    public FileSystemId convert(@Nullable String fileSystemIdString) {
        return fileSystemIdString == null
            ? null
            : FileSystemId.create(fileSystemIdString);
    }
}
