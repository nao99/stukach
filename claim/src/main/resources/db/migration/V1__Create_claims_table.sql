CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE claims (
    id         UUID NOT NULL DEFAULT uuid_generate_v4(),
    user_id    UUID NOT NULL,
    status     VARCHAR (64) NOT NULL DEFAULT 'CREATED',
    created_at TIMESTAMPTZ NOT NULL DEFAULT current_timestamp(0),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT current_timestamp(0),

    CONSTRAINT pk_claims__id PRIMARY KEY (id)
);
