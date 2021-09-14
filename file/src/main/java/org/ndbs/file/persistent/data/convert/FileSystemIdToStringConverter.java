package org.ndbs.file.persistent.data.convert;

import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * FileSystemIdToStringConverter class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@Component
@WritingConverter
public class FileSystemIdToStringConverter implements Converter<FileSystemId, String> {
    @Override
    @Nullable
    public String convert(@Nullable FileSystemId fileSystemId) {
        return fileSystemId == null
            ? null
            : fileSystemId.toString();
    }
}
