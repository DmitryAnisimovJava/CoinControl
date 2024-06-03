CREATE TABLE coin_repository.expired_tokens
(
    id         UUID PRIMARY KEY,
    keep_until TIMESTAMP NOT NULL CHECK ( keep_until > now())

);
