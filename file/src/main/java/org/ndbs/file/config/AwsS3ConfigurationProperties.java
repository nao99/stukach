package org.ndbs.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * AwsS3ConfigurationProperties class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-14
 */
@ConfigurationProperties("app.aws")
@ConstructorBinding
public class AwsS3ConfigurationProperties {
    @NestedConfigurationProperty
    private final Credentials credentials;
    private final String protocol;
    private final String endpoint;
    private final String signerOverride;
    private final boolean pathStyleAccessEnabled;

    public AwsS3ConfigurationProperties(
        Credentials credentials,
        String protocol,
        String endpoint,
        String signerOverride,
        boolean pathStyleAccessEnabled
    ) {
        this.credentials = credentials;
        this.protocol = protocol;
        this.endpoint = endpoint;
        this.signerOverride = signerOverride;
        this.pathStyleAccessEnabled = pathStyleAccessEnabled;
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

    public String getEndpoint() {
        return endpoint;
    }

    public String getSignerOverride() {
        return signerOverride;
    }

    public boolean isPathStyleAccessEnabled() {
        return pathStyleAccessEnabled;
    }
}
