-- liquibase formatted sql

-- changeset gordey_dovydenko:1

CREATE
    EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE Users
(
    id            UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    username      VARCHAR(100) NOT NULL UNIQUE,
    email         VARCHAR(200) NOT NULL UNIQUE,
    password      VARCHAR(600) NOT NULL,
    full_name     VARCHAR(200) NOT NULL,
    online_status BOOLEAN      NOT NULL DEFAULT FALSE
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
    chat_room_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sender_id    UUID NOT NULL,
    receiver_id  UUID NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES Users (id),
    FOREIGN KEY (receiver_id) REFERENCES Users (id)
);

-- rollback DROP TABLE chat_room;

-- changeset T9404:4
CREATE TABLE IF NOT EXISTS message
(
    message_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chat_room_id UUID         NOT NULL,
    sender_id    UUID         NOT NULL,
    receiver_id  UUID         NOT NULL,
    content      VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (chat_room_id),
    FOREIGN KEY (sender_id) REFERENCES Users (id),
    FOREIGN KEY (receiver_id) REFERENCES Users (id)
);

-- rollback DROP TABLE message;

-- changeset gordey_dovydenko:5

CREATE TABLE IF NOT EXISTS defect_status
(
    id   SERIAL PRIMARY KEY,
    name TEXT,
    has_distance BOOLEAN DEFAULT FALSE
);

-- rollback DROP TABLE defect_status;

-- changeset gordey_dovydenko:6

CREATE TABLE IF NOT EXISTS application
(
    application_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    creator_id       UUID                     NOT NULL,
    executor_id      UUID,
    longitude        DOUBLE PRECISION         NOT NULL,
    latitude         DOUBLE PRECISION         NOT NULL,
    process_status   VARCHAR(50) CHECK ( process_status IN
                                         ('OPEN', 'REJECTED', 'IN_PROCESS', 'WAIT_ACCEPT', 'COMPLETED') ),
    defect_status_id SERIAL                   NOT NULL,
    description      TEXT,
    address          TEXT,
    created_time     TIMESTAMP WITH TIME ZONE NOT NULL,
    deadline_time    TIMESTAMP WITH TIME ZONE NOT NULL,
    defect_distance  DOUBLE PRECISION,
    FOREIGN KEY (creator_id) REFERENCES Users (id),
    FOREIGN KEY (executor_id) REFERENCES Users (id),
    FOREIGN KEY (defect_status_id) REFERENCES defect_status (id)
);

-- rollback DROP TABLE application;

-- changeset gordey_dovydenko:7

CREATE TABLE IF NOT EXISTS application_photo
(
    photo_id       VARCHAR(100) NOT NULL PRIMARY KEY,
    application_id UUID         NOT NULL,
    FOREIGN KEY (application_id) REFERENCES application (application_id)
);

-- rollback DROP TABLE application_photo;

-- changeset gordey_dovydenko:8

CREATE TABLE IF NOT EXISTS application_audio
(
    application_id   UUID PRIMARY KEY,
    audio_id VARCHAR(100) NOT NULL,
    FOREIGN KEY (application_id) REFERENCES application (application_id)
);

-- rollback DROP TABLE application_audio;

-- changeset gordey_dovydenko:9

CREATE INDEX IF NOT EXISTS idx_application_longitude_latitude ON application USING btree (longitude, latitude);
CREATE INDEX IF NOT EXISTS idx_application_process_status ON application USING hash (process_status);
CREATE INDEX IF NOT EXISTS idx_application_defect_status_id ON application USING hash (defect_status_id);

-- rollback DROP INDEX idx_application_longitude_latitude;
-- rollback DROP INDEX idx_application_process_status;
-- rollback DROP INDEX idx_application_defect_status_id;

-- changeset gordey_dovydenko:10
INSERT INTO defect_status (name, has_distance) VALUES
    ('Стирание краски', TRUE),
    ('Сминание', TRUE),
    ('Изгибание', TRUE),
    ('Нарушение целостности дорожного знака', FALSE),
    ('Вандализм', FALSE),
    ('Перекрытие растительностью', TRUE),
    ('Снежный налет', TRUE),
    ('Ремонт', TRUE),
    ('Разрушение', TRUE),
    ('Продольная трещина', TRUE),
    ('Яма', TRUE),
    ('Трещина поперечная', TRUE),
    ('Стирание разметки', TRUE),
    ('Люк', FALSE),
    ('Размытие пешеходного перехода', TRUE),
    ('Разрушение бордюрного камня', TRUE),
    ('Железная балка', TRUE),
    ('Бетонный блок', TRUE),
    ('Железный забор', TRUE),
    ('Тросовый забор', TRUE);

-- rollback DELETE FROM defect_status;

-- changeset gordey_dovydenko:11

INSERT INTO Users (username, email, password, full_name, online_status)
VALUES ('admin', 'admin@gmail.ru', '$2a$10$p/SE0M1upWg1Szb3eGLn0.t4lLC5hbOpmL.VuY0CpTQYmiP8kXG/W', 'Admin', TRUE);

INSERT INTO User_Authorities (user_id, authorities)
VALUES ((SELECT id FROM Users WHERE username = 'admin'), 'ROLE_ADMIN');

-- rollback DELETE FROM User_Authorities WHERE user_id = (SELECT id FROM Users WHERE username = 'admin');
-- rollback DELETE FROM Users WHERE username = 'admin';

-- changeset gordey_dovydenko:12

CREATE TABLE voice_scheduler
(
    application_id UUID PRIMARY KEY,
    process_id VARCHAR(60) NOT NULL,
    schedule_time   TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(50) CHECK ( status IN ('IN_PROCESS', 'EXECUTED') ),
    FOREIGN KEY (application_id) REFERENCES application (application_id)
);

-- rollback DROP TABLE voice_scheduler;