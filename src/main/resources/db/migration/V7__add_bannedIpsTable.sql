CREATE TABLE coin_repository.banned_user_ip
(
    id    UUID PRIMARY KEY,
    banned_ip CIDR NOT NULL
);
