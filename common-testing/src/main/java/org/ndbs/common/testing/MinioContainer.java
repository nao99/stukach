package org.ndbs.common.testing;

import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;

import java.time.Duration;

/**
 * MinioContainer class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-16
 */
public class MinioContainer<SELF extends MinioContainer<SELF>> extends GenericContainer<SELF> {
    private final String accessKey = "s3key";
    private final String secretKey = "s3secret";

    private final int port = 9000;
    private final String protocol = "HTTP";

    private final String signerOverride = "AWSS3V4SignerType";
    private final Boolean pathStyleAccessEnabled = true;

    private final String bucket = "s3-default";

    private final String minioMcImage;

    public MinioContainer(String minioImage, String minioMcImage) {
        super(minioImage);

        this.minioMcImage = minioMcImage;

        waitStrategy = new HttpWaitStrategy()
            .forPath("/minio/health/ready")
            .forPort(port)
            .withStartupTimeout(Duration.ofSeconds(60));

        setCommand("server /data");
        addExposedPorts(port);
    }

    @Override
    protected void configure() {
        addEnv("MINIO_ACCESS_KEY", accessKey);
        addEnv("MINIO_SECRET_KEY", secretKey);
    }

    @Override
    public void start() {
        super.start();

        var mappedPort = getFirstMappedPort();
        Testcontainers.exposeHostPorts(mappedPort);

        try (var mcContainer = new MinioMcContainer<>(minioMcImage, mappedPort, accessKey, secretKey, bucket)) {
            mcContainer.start();
        }
    }

    private static class MinioMcContainer<SELF extends MinioMcContainer<SELF>> extends GenericContainer<SELF> {
        private MinioMcContainer(String image, int port, String accessKey, String secretKey, String bucket) {
            super(image);

            var commandPattern = "" +
                "mc config host add myminio http://host.testcontainers.internal:%d/ %s %s --api s3v4 && " +
                "mc mb myminio/%s";

            var command = String.format(commandPattern, port, accessKey, secretKey, bucket);

            withStartupCheckStrategy(new OneShotStartupCheckStrategy());
            withCreateContainerCmdModifier(createContainerCmd -> createContainerCmd
                .withTty(true)
                .withEntrypoint("/bin/sh", "-c", command)
            );
        }
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getSignerOverride() {
        return signerOverride;
    }

    public Boolean getPathStyleAccessEnabled() {
        return pathStyleAccessEnabled;
    }

    public String getUri() {
        var host = getHost();
        return String.format("s3://%s@%s:%d", accessKey, host, port);
    }

    public String getBucket() {
        return bucket;
    }
}
