-- liquibase formatted sql

-- changeset gordey_dovydenko:1

CREATE
    EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Users
(
    id        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username  VARCHAR(100) NOT NULL UNIQUE,
    email     VARCHAR(200) NOT NULL UNIQUE,
    password  VARCHAR(600) NOT NULL,
    full_name VARCHAR(200) NOT NULL,
    online_status BOOLEAN NOT NULL DEFAULT FALSE
);

-- rollback DROP TABLE Users;

-- changeset gordey_dovydenko:2

CREATE TABLE User_Authorities
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id     UUID         NOT NULL,
    authorities VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

-- rollback DROP TABLE User_Authorities;

-- changeset T9404:3
CREATE TABLE IF NOT EXISTS chat_room
(
    chat_room_id  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sender_id     UUID NOT NULL,
    receiver_id   UUID NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES Users (id),
    FOREIGN KEY (receiver_id) REFERENCES Users (id)
);

-- rollback DROP TABLE chat_room;

-- changeset T9404:4
CREATE TABLE IF NOT EXISTS message
(
    message_id  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chat_room_id UUID NOT NULL,
    sender_id    UUID NOT NULL,
    receiver_id  UUID NOT NULL,
    content      VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (chat_room_id),
    FOREIGN KEY (sender_id) REFERENCES Users (id),
    FOREIGN KEY (receiver_id) REFERENCES Users (id)
);

-- rollback DROP TABLE message;
