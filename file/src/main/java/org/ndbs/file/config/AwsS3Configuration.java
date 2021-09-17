package org.ndbs.file.config;

import com.upplication.s3fs.AmazonS3Factory;
import com.upplication.s3fs.S3FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
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
    private final AwsS3ConfigurationProperties s3ConfigurationProperties;

    @Autowired
    public AwsS3Configuration(AwsS3ConfigurationProperties s3ConfigurationProperties) {
        this.s3ConfigurationProperties = s3ConfigurationProperties;
    }

    @Bean("amazonS3FileSystem")
    public FileSystem amazonS3FileSystem() throws IOException {
        var s3Uri = s3ConfigurationProperties.getUri();
        var env = mapS3PropertiesToMap();

        return FileSystems.newFileSystem(s3Uri, env);
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

        propertiesMap.put(AmazonS3Factory.ACCESS_KEY, credentials.getAccessKey());
        propertiesMap.put(AmazonS3Factory.SECRET_KEY, credentials.getSecretKey());
        propertiesMap.put(AmazonS3Factory.PROTOCOL, s3ConfigurationProperties.getProtocol());
        propertiesMap.put(AmazonS3Factory.SIGNER_OVERRIDE, s3ConfigurationProperties.getSignerOverride());
        propertiesMap.put(AmazonS3Factory.PATH_STYLE_ACCESS, s3ConfigurationProperties.isPathStyleAccessEnabled());

        return propertiesMap;
    }
}
