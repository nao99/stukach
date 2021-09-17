package org.ndbs.common.testing;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * IntegrationTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 1.0.0
 * @since   2021-09-16
 */
@SpringBootTest
@ContextConfiguration(initializers = {
    DatabaseTest.Initializer.class,
    FileStorageTest.Initializer.class
})
public abstract class IntegrationTest {
}
