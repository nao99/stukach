package org.ndbs.filesystem.domain.path.model;

import org.ndbs.filesystem.domain.path.PathStrategyException;
import org.ndbs.filesystem.domain.filesystem.model.File;

/**
 * DefaultPathStrategy class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2020-07-12
 */
public class DefaultPathStrategy extends AbstractPathStrategy {
    public static final String NAME = "default";

    private DefaultPathStrategy() {
        super(NAME);
    }

    public static DefaultPathStrategy create() {
        return new DefaultPathStrategy();
    }

    @Override
    protected String buildDirectoryPath(File file) throws PathStrategyException {
        var fileId = file.getId();
        if (fileId == null) {
            throw new PathStrategyException("File id should be not null");
        }

        var fileIdString = fileId.toString();
        var fileIdParts = fileIdString.split("-");

        var stringBuilder = new StringBuilder("/");
        for (var fileIdPart : fileIdParts) {
            stringBuilder.append(fileIdPart);
            stringBuilder.append("/");
        }

        return stringBuilder.toString();
    }
}
