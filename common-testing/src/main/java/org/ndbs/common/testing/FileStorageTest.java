package org.ndbs.common.testing;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * FileStorageTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-16
 */
@SpringBootTest
@ContextConfiguration(initializers = {FileStorageTest.Initializer.class})
public abstract class FileStorageTest {
    private static final MinioContainerShared MINIO_CONTAINER = MinioContainerShared.getInstance();

    static {
        MINIO_CONTAINER.start();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            var testPropertyValues = TestPropertyValues.of(
                "app.aws.client.credentials.accessKey=" + MINIO_CONTAINER.getAccessKey(),
                "app.aws.client.credentials.secretKey=" + MINIO_CONTAINER.getSecretKey(),
                "app.aws.client.protocol=" + MINIO_CONTAINER.getProtocol(),
                "app.aws.client.signerOverride=" + MINIO_CONTAINER.getSignerOverride(),
                "app.aws.client.pathStyleAccessEnabled=" + MINIO_CONTAINER.getPathStyleAccessEnabled(),
                "app.aws.client.uri=" + MINIO_CONTAINER.getUri(),
                "app.aws.bucket=" + MINIO_CONTAINER.getBucket()
            );

            testPropertyValues.applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    private FileStorageTest() {}
}
