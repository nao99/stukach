package org.ndbs.filesystem.domain.model;

import java.util.UUID;

/**
 * AwesomeFile class
 *
 * @author  Nikolai Osipov <nao99.dev@gmail.com>
 * @version 2.0.0
 * @since   2021-09-09
 */
public class AwesomeFile implements File {
    private final UUID id;
    private final String name;

    private AwesomeFile(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AwesomeFile create(UUID id, String name) {
        return new AwesomeFile(id, name);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
