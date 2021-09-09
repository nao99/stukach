package org.ndbs.filesystem.domain.registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.PathStrategyRegistryException;
import org.ndbs.filesystem.domain.model.strategy.AwesomePathStrategy;
import org.ndbs.filesystem.domain.model.strategy.DefaultPathStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * PathStrategyRegistryTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
@DisplayName("PathStrategyRegistry test: Test for te Path Strategy Registry")
class PathStrategyRegistryTest {
    private PathStrategyRegistry registry;

    @BeforeEach
    public void setUp() throws Exception {
        registry = PathStrategyRegistry.create();
    }

    @DisplayName("Should has a default strategy and has not another after creation")
    @Test
    void shouldHasDefaultStrategyAndHasNotAnotherAfterCreation() throws Exception {
        // when
        var hasDefaultStrategy = registry.hasStrategy(DefaultPathStrategy.NAME);
        var hasUnknownStrategy = registry.hasStrategy("test_path_strategy_name");

        // then
        assertThat(hasDefaultStrategy)
            .isTrue();

        assertThat(hasUnknownStrategy)
            .isFalse();
    }

    @DisplayName("Should successfully add a new strategy")
    @Test
    void shouldSuccessfullyAddNewStrategy() throws Exception {
        // given
        var expectedStrategyName = "awesome_path_strategy";
        var newStrategy = AwesomePathStrategy.create(expectedStrategyName);

        // when
        registry.addStrategy(newStrategy);
        var hasAwesomeStrategy = registry.hasStrategy(expectedStrategyName);

        // then
        assertThat(hasAwesomeStrategy)
            .isTrue();
    }

    @DisplayName("Should get a default strategy")
    @Test
    void shouldGetDefaultStrategy() throws Exception {
        // when
        var strategy = registry.getStrategy(DefaultPathStrategy.NAME);

        // then
        assertThat(strategy.getName())
            .isEqualTo(DefaultPathStrategy.NAME);
    }

    @DisplayName("Should throw an exception when registry does not contain a strategy")
    @Test
    void shouldThrowExceptionWhenRegistryDoesNotContainStrategy() throws Exception {
        // when / then
        assertThrows(PathStrategyRegistryException.class, () -> registry.getStrategy("test_path_strategy_name"));
    }
}
