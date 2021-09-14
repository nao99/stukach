package org.ndbs.file.config;

import com.upplication.s3fs.S3FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

/**
 * AwsConfiguration class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-09
 */
@Configuration
@EnableConfigurationProperties({AwsS3ConfigurationProperties.class})
public class AwsS3Configuration {
    private static final String S3_URI_PATTERN = "s3://%s@%s";

    private final AwsS3ConfigurationProperties s3ConfigurationProperties;

    @Autowired
    public AwsS3Configuration(AwsS3ConfigurationProperties s3ConfigurationProperties) {
        this.s3ConfigurationProperties = s3ConfigurationProperties;
    }

    @Bean
    public FileSystem amazonS3FileSystem() throws IOException {
        var s3Uri = buildS3Uri();
        var env = mapS3PropertiesToMap();

        return FileSystems.newFileSystem(s3Uri, env);
    }

    /**
     * Builds a {@link URI} to ease tune the {@link S3FileSystem} <br>
     * It requires the: <br>
     *  - s3 (schema) <br>
     *  - access key (authority) <br>
     *  - endpoint (host) <br>
     *
     * What does a URI consist of - see
     * <a href="https://en.wikipedia.org/wiki/Uniform_Resource_Identifier#Syntax">this page</a>
     *
     * @return an s3 uri
     */
    private URI buildS3Uri() {
        var credentials = s3ConfigurationProperties.getCredentials();

        var accessKey = credentials.getAccessKey();
        var endpoint = s3ConfigurationProperties.getEndpoint();

        var s3UriString = String.format(S3_URI_PATTERN, accessKey, endpoint);

        return URI.create(s3UriString);
    }

    /**
     * Maps properties from the {@link AwsS3ConfigurationProperties} to a simple map <br>
     * It required to ease tune the {@link S3FileSystem} <br>
     *
     * Everything map keys are certain AWS S3 properties <br>
     * The full properties list you can see <a href="https://github.com/Upplication/Amazon-S3-FileSystem-NIO2">here</a>
     *
     * @return mapped properties
     */
    private Map<String, Object> mapS3PropertiesToMap() {
        Map<String, Object> propertiesMap = new HashMap<>();

        var credentials = s3ConfigurationProperties.getCredentials();

        propertiesMap.put("s3fs_access_key", credentials.getAccessKey());
        propertiesMap.put("s3fs_secret_key", credentials.getSecretKey());
        propertiesMap.put("s3fs_protocol", s3ConfigurationProperties.getProtocol());
        propertiesMap.put("s3fs_signer_override", s3ConfigurationProperties.getSignerOverride());
        propertiesMap.put("s3fs_path_style_access", s3ConfigurationProperties.isPathStyleAccessEnabled());

        return propertiesMap;
    }
}
