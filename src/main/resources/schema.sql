CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS chatrooms
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR NOT NULL,
    owner BIGINT  REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS messages
(
    id        BIGSERIAL PRIMARY KEY,
    sender    BIGINT  REFERENCES users (id),
    room      BIGINT  REFERENCES chatrooms (id),
    message   TEXT      NOT NULL,
    date_time TIMESTAMP default CURRENT_TIMESTAMP
);