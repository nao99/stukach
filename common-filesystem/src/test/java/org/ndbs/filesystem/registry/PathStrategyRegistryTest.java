package org.ndbs.filesystem.registry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ndbs.filesystem.domain.path.PathStrategyNotFoundException;
import org.ndbs.filesystem.domain.path.model.AwesomePathStrategy;
import org.ndbs.filesystem.domain.path.model.DefaultPathStrategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * PathStrategyRegistryTest class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
@DisplayName("PathStrategyRegistry test: Test for the Path Strategy Registry")
class PathStrategyRegistryTest {
    private PathStrategyRegistry registry;

    @BeforeEach
    public void setUp() throws Exception {
        registry = PathStrategyRegistry.create();
    }

    @DisplayName("Should not has default strategy after creation")
    @Test
    void shouldNotHasDefaultStrategyAfterCreation() throws Exception {
        // when
        var hasDefaultStrategy = registry.hasStrategy(DefaultPathStrategy.NAME);
        var hasUnknownStrategy = registry.hasStrategy("test_path_strategy_name");

        // then
        assertThat(hasDefaultStrategy)
            .isFalse();

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

    @DisplayName("Should throw an exception when registry does not contain a strategy")
    @Test
    void shouldThrowExceptionWhenRegistryDoesNotContainStrategy() throws Exception {
        // when / then
        assertThrows(PathStrategyNotFoundException.class, () -> registry.getStrategy("test_path_strategy_name"));
    }

    @DisplayName("Should remove default strategy from registry if exists")
    @Test
    void shouldRemoveDefaultStrategyFromRegistryInExists() throws Exception {
        // given
        var strategyName = "awesome_path_strategy";
        var strategy = AwesomePathStrategy.create(strategyName);

        // when / then
        registry.addStrategy(strategy);
        assertThat(registry.hasStrategy(strategyName))
            .isTrue();

        registry.removeStrategy(strategyName);
        assertThat(registry.hasStrategy(strategyName))
            .isFalse();
    }

    @DisplayName("Should not throw any exception if strategy does not exist in registry")
    @Test
    void shouldNotThrowAnyExceptionIfStrategyDoesNotExistInRegistry() throws Exception {
        // given
        var strategyName = "non_existed_path_strategy";

        // when / then
        assertDoesNotThrow(() -> registry.removeStrategy(strategyName));
    }
}
