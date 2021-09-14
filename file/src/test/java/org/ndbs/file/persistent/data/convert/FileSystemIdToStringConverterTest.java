package org.ndbs.file.persistent.data.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemId;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FileSystemIdToStringConverterTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@DisplayName("FileSystemIdToStringConverter test: Test for the File System Id To String Converter")
class FileSystemIdToStringConverterTest {
    private FileSystemIdToStringConverter converter;

    @BeforeEach
    void setUp() throws Exception {
        converter = new FileSystemIdToStringConverter();
    }

    @DisplayName("Should return \"s3\" string if filesystem id is \"s3\"")
    @Test
    void shouldReturnS3StringIfFileSystemIdIsS3() throws Exception {
        // given
        var expectedFileSystemIdString = "s3";
        var fileSystemId = FileSystemId.create(expectedFileSystemIdString);

        // when
        var fileSystemIdString = converter.convert(fileSystemId);

        // then
        assertThat(fileSystemIdString)
            .isEqualTo(expectedFileSystemIdString);
    }

    @DisplayName("Should return null if filesystem id is null")
    @Test
    void shouldReturnNullIfFileSystemIdIsNull() throws Exception {
        // when
        var fileSystemIdString = converter.convert(null);

        // then
        assertThat(fileSystemIdString)
            .isNull();
    }
}
