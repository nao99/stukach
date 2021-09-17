package org.ndbs.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.net.URI;

/**
 * AwsS3ConfigurationProperties class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@ConfigurationProperties("app.aws.client")
@ConstructorBinding
public class AwsS3ConfigurationProperties {
    @NestedConfigurationProperty
    private final Credentials credentials;
    private final String protocol;
    private final String signerOverride;
    private final String pathStyleAccessEnabled;
    private final URI uri;

    public AwsS3ConfigurationProperties(
        Credentials credentials,
        String protocol,
        String signerOverride,
        String pathStyleAccessEnabled,
        URI uri
    ) {
        this.credentials = credentials;
        this.protocol = protocol;
        this.signerOverride = signerOverride;
        this.pathStyleAccessEnabled = pathStyleAccessEnabled;
        this.uri = uri;
    }

    @ConstructorBinding
    public static class Credentials {
        private final String accessKey;
        private final String secretKey;

        public Credentials(String accessKey, String secretKey) {
            this.accessKey = accessKey;
            this.secretKey = secretKey;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getProtocol() {
        return protocol;
    }

    public URI getUri() {
        return uri;
    }

    public String getSignerOverride() {
        return signerOverride;
    }

    public String isPathStyleAccessEnabled() {
        return pathStyleAccessEnabled;
    }
}
