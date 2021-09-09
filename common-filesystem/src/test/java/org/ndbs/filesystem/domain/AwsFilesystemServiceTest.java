package org.ndbs.filesystem.domain;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.model.Filesystem;
import org.ndbs.filesystem.domain.model.FilesystemResource;

import java.io.File;
import java.net.URL;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * AwsFilesystemServiceTest class
 *
 * TODO: Rewrite these tests
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
@DisplayName("AwsFilesystemService test: Test for AWS Filesystem Service")
class AwsFilesystemServiceTest {
    private AmazonS3 client;
    private AwsFilesystemService awsFilesystemService;

    private File tempFile;

    @BeforeEach
    void setUp() throws Exception {
        client = mock(AmazonS3.class);
        awsFilesystemService = new AwsFilesystemService(client);
        tempFile = File.createTempFile("station_au", ".ts");
    }

    @DisplayName("Should copy from source to destination")
    @Test
    void shouldCopyFromSourceToDestination() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());

        // when / then
        awsFilesystemService.copy(source, source);

        verify(client, times(0))
            .putObject(any());

        verify(client, times(0))
            .copyObject(any());
    }

    @DisplayName("Should copy from local to destination")
    @Test
    void shouldCopyFromLocalToDestination() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());
        var destination = FilesystemResource.asRoot(tempFile.getPath() + "/test_directory");

        // when / then
        awsFilesystemService.copy(source, destination);

        verify(client, times(1))
            .putObject(anyString(), anyString(), any(File.class));
    }

    @DisplayName("Should throw an exception when unable to put resource to AWS (Client's problem)")
    @Test
    void shouldThrowExceptionWhenUnableToPutResourceToAwsClient() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());
        var destination = FilesystemResource.asRoot(tempFile.getPath() + "/test_directory");

        // when / then
        when(client.putObject(anyString(), anyString(), any(File.class)))
            .thenThrow(new AmazonClientException("Test"));

        assertThrows(FilesystemException.class, () -> awsFilesystemService.copy(source, destination));
    }

    @DisplayName("Should throw an exception when unable to put resource to AWS (Service's problem)")
    @Test
    void shouldThrowExceptionWhenUnableToPutResourceToAwsService() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());
        var destination = FilesystemResource.asRoot(tempFile.getPath() + "/test_directory");

        // when / then
        when(client.putObject(anyString(), anyString(), any(File.class)))
            .thenThrow(new AmazonServiceException("Test"));

        assertThrows(FilesystemException.class, () -> awsFilesystemService.copy(source, destination));
    }

    @DisplayName("Should copy from remote")
    @Test
    void shouldCopyFromRemote() throws Exception {
        // given
        var source = FilesystemResource.create(Filesystem.DEFAULT, tempFile.getPath());
        var destination = FilesystemResource.create(Filesystem.DEFAULT, tempFile.getPath() + "/test_directory");

        // when / then
        awsFilesystemService.copy(source, destination);

        verify(client, times(1))
            .copyObject(
                source.getFilesystem().toString(),
                source.getAbsolutePath(),
                destination.getFilesystem().toString(),
                destination.getAbsolutePath()
            );
    }

    @DisplayName("Should not copy from remote when an exception was thrown (Client's problem)")
    @Test
    void shouldNotCopyFromRemoteWhenExceptionWasThrownClient() throws Exception {
        // given
        var source = FilesystemResource.create(Filesystem.DEFAULT, tempFile.getPath());
        var destination = FilesystemResource.create(Filesystem.DEFAULT, tempFile.getPath() + "/test_directory");

        // when / then
        when(client.copyObject(
                source.getFilesystem().toString(),
                source.getAbsolutePath(),
                destination.getFilesystem().toString(),
                destination.getAbsolutePath()
            ))
            .thenThrow(new AmazonClientException("Test"));

        assertThrows(FilesystemException.class, () -> awsFilesystemService.copy(source, destination));
    }

    @DisplayName("Should not copy from remote when an exception was thrown (Service's problem)")
    @Test
    void shouldNotCopyFromRemoteWhenExceptionWasThrownService() throws Exception {
        // given
        var source = FilesystemResource.create(Filesystem.DEFAULT, tempFile.getPath());
        var destination = FilesystemResource.create(Filesystem.DEFAULT, tempFile.getPath() + "/test_directory");

        // when / then
        when(client.copyObject(
                source.getFilesystem().toString(),
                source.getAbsolutePath(),
                destination.getFilesystem().toString(),
                destination.getAbsolutePath()
            ))
            .thenThrow(new AmazonServiceException("Test"));

        assertThrows(FilesystemException.class, () -> awsFilesystemService.copy(source, destination));
    }

    @DisplayName("Should move a source to destination")
    @Test
    void shouldMoveSourceToDestination() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());
        var destination = FilesystemResource.asRoot(tempFile.getPath() + "/test_directory");

        awsFilesystemService = spy(new AwsFilesystemService(client));

        // when /then
        awsFilesystemService.move(source, destination);

        verify(awsFilesystemService, times(1))
            .copy(source, destination);

        verify(awsFilesystemService, times(1))
            .delete(source);
    }

    @DisplayName("Should delete a resource")
    @Test
    void shouldDeleteResource() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());

        // when / then
        awsFilesystemService.delete(source);

        verify(client, times(1))
            .deleteObject(source.getFilesystem().toString(), source.getAbsolutePath());
    }

    @DisplayName("Should not delete a resource when an exception was thrown (Client's problem)")
    @Test
    void shouldNotDeleteResourceWhenExceptionWasThrownClient() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());

        // when / then
        doThrow(new AmazonClientException("Test"))
            .when(client)
            .deleteObject(source.getFilesystem().toString(), source.getAbsolutePath());

        assertThrows(FilesystemException.class, () -> awsFilesystemService.delete(source));
    }

    @DisplayName("Should not delete a resource when an exception was thrown (Service's problem)")
    @Test
    void shouldNotDeleteResourceWhenExceptionWasThrownService() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());

        // when / then
        doThrow(new AmazonServiceException("Test"))
            .when(client)
            .deleteObject(source.getFilesystem().toString(), source.getAbsolutePath());

        assertThrows(FilesystemException.class, () -> awsFilesystemService.delete(source));
    }

    @DisplayName("Should get a public URL to resource")
    @Test
    void shouldGetPublicUrlToResource() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());
        var urlStr = "http://www.test_url.com";

        // when
        when(client.generatePresignedUrl(
                anyString(),
                anyString(),
                any(Date.class)
            ))
            .thenReturn(new URL(urlStr));

        var result = awsFilesystemService.getUrl(source);

        // then
        assertThat(result.toString())
            .isEqualTo(urlStr);
    }

    @DisplayName("Should not get a public URL when an exception was thrown")
    @Test
    void shouldNotGetPublicUrlWhenExceptionWasThrown() throws Exception {
        // given
        var source = FilesystemResource.asRoot(tempFile.getPath());

        // when / then
        when(client.generatePresignedUrl(
                anyString(),
                anyString(),
                any(Date.class)
            ))
            .thenThrow(new AmazonClientException("Test"));

        assertThrows(FilesystemException.class, () -> awsFilesystemService.getUrl(source));
    }
}
