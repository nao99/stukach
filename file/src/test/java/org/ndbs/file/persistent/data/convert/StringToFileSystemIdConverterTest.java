package org.ndbs.file.persistent.data.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StringToFileSystemIdConverterTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@DisplayName("StringToFileSystemIdConverter test: Test for the String To File System Id Converter")
class StringToFileSystemIdConverterTest {
    private StringToFileSystemIdConverter stringToFileSystemIdConverter;

    @BeforeEach
    void setUp() throws Exception {
        stringToFileSystemIdConverter = new StringToFileSystemIdConverter();
    }

    @DisplayName("Should return \"s3-default\" filesystem id in object if id is \"s3-default\" string")
    @Test
    void shouldReturnS3DefaultFileSystemIdInObjectIfIdIsS3DefaultString() throws Exception {
        // given
        var fileSystemIdString = "s3-default";
        var expectedFileSystemId = FileSystemId.create(fileSystemIdString);

        // when
        var fileSystemId = stringToFileSystemIdConverter.convert(fileSystemIdString);

        // then
        assertThat(fileSystemId)
            .isEqualTo(expectedFileSystemId);
    }

    @DisplayName("Should return null if id is null")
    @Test
    void shouldReturnNullIfIdIsNull() throws Exception {
        // when
        var fileSystemId = stringToFileSystemIdConverter.convert(null);

        // then
        assertThat(fileSystemId)
            .isNull();
    }
}
