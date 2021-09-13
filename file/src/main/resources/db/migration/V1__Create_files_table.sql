CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE files (
    id            UUID NOT NULL DEFAULT uuid_generate_v4(),
    filesystem_id VARCHAR (16) NOT NULL DEFAULT 's3-default',
    path_strategy VARCHAR (16) NOT NULL DEFAULT 'default',
    mime_type     VARCHAR(255) NOT NULL,
    basename      VARCHAR(255) NOT NULL,
    filesize      BIGINT NOT NULL,
    confirmed     BOOLEAN DEFAULT false NOT NULL,
    deleted       BOOLEAN DEFAULT false NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL DEFAULT current_timestamp(0),
    updated_at    TIMESTAMPTZ NOT NULL DEFAULT current_timestamp(0),

    CONSTRAINT pk_files__id PRIMARY KEY (id)
);
