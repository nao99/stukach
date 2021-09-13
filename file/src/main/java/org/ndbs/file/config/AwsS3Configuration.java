package org.ndbs.file.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.upplication.s3fs.S3FileSystem;
import com.upplication.s3fs.S3FileSystemProvider;
import org.ndbs.filesystem.domain.filesystem.FileSystemServiceImpl;
import org.ndbs.filesystem.domain.filesystem.FileSystemService;
import org.ndbs.filesystem.domain.filesystem.model.FileSystemImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystem;

/**
 * AwsConfiguration class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
@Configuration
public class AwsS3Configuration {
    @Value("${app.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${app.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${app.aws.region.static}")
    private String region;

    @Value("${app.aws.endpoint}")
    private String endpoint;

    @Value("${app.aws.signerOverride}")
    private String signerOverride;

    @Value("${app.aws.pathStyleAccessEnabled}")
    private Boolean pathStyleAccessEnabled;

    @Bean
    public AmazonS3 amazonS3() {
        var awsEndpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);

        var clientConfiguration = new ClientConfiguration()
            .withSignerOverride(signerOverride);

        var awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        var awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

        return AmazonS3Client.builder()
            .withEndpointConfiguration(awsEndpointConfiguration)
            .withPathStyleAccessEnabled(pathStyleAccessEnabled)
            .withClientConfiguration(clientConfiguration)
            .withCredentials(awsStaticCredentialsProvider)
            .build();
    }

    @Bean
    public FileSystem amazonS3FileSystem() {
        var s3FileSystemProvider = new S3FileSystemProvider();
        var client = amazonS3();

        return new S3FileSystem(s3FileSystemProvider, null, client, endpoint);
    }

    @Bean
    public org.ndbs.filesystem.domain.filesystem.model.FileSystem fileSystem() {
        return FileSystemImpl.create();
    }

    @Bean
    public FileSystemService filesystemService() {
        var fileSystem = fileSystem();
        return FileSystemServiceImpl.create(fileSystem);
    }
}
