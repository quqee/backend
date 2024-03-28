-- liquibase formatted sql

-- changeset gordey_dovydenko:1

CREATE
    EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE organization
(
    organization_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name              VARCHAR(100) NOT NULL,
    address           VARCHAR(255) NOT NULL,
    phone_number      VARCHAR(20)  NOT NULL UNIQUE,
    email             VARCHAR(255) NOT NULL UNIQUE,
    organization_type VARCHAR(255) NOT NULL CHECK ( organization_type IN ('CUSTOMER', 'PERFORMER') )
);

-- rollback DROP TABLE organization;

-- changeset T9404:2

CREATE TABLE users
(
    user_id         UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    organization_id UUID         NOT NULL,
    username        VARCHAR(100) NOT NULL UNIQUE,
    email           VARCHAR(200) NOT NULL UNIQUE,
    password        VARCHAR(600) NOT NULL,
    full_name       VARCHAR(200) NOT NULL,
    online_status   BOOLEAN      NOT NULL DEFAULT FALSE,
    FOREIGN KEY (organization_id) REFERENCES organization (organization_id)
);

-- rollback DROP TABLE users;

-- changeset gordey_dovydenko:3


CREATE TABLE user_authority
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id     UUID         NOT NULL,
    authorities VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- rollback DROP TABLE User_Authorities;

-- changeset T9404:3

CREATE TABLE statement
(
    statement_id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    organization_creator_id   UUID                     NOT NULL,
    organization_performer_id UUID,
    create_time               TIMESTAMP WITH TIME ZONE NOT NULL,
    area_name                 VARCHAR(255)             NOT NULL,
    length                    DOUBLE PRECISION         NOT NULL,
    road_type                 VARCHAR(255)             NOT NULL CHECK ( road_type IN ('HIGHWAY', 'EXPRESSWAY', 'ROAD') ),
    surface_type              VARCHAR(255)             NOT NULL CHECK ( surface_type IN
                                                                        ('ASPHALT', 'COBBLESTONE', 'CRUSHED_STONE', 'GROUND',
                                                                         'SAND',
                                                                         'CONCRETE', 'REINFORCED_CONCRETE', 'COMBINED',
                                                                         'OTHER') ),
    statement_status VARCHAR(50) CHECK ( statement_status IN
                                           ('OPEN', 'REJECTED', 'IN_PROCESS', 'WAIT_ACCEPT', 'COMPLETED') ),
    direction                 VARCHAR(255),
    deadline                  TIMESTAMP WITH TIME ZONE NOT NULL,
    description               VARCHAR(255),
    FOREIGN KEY (organization_creator_id) REFERENCES organization (organization_id),
    FOREIGN KEY (organization_performer_id) REFERENCES organization (organization_id)
);


-- rollback DROP TABLE statement;

-- changeset T9404:4

CREATE TABLE executor
(
    user_id      UUID NOT NULL,
    statement_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (statement_id) REFERENCES statement (statement_id)
);

-- rollback DROP TABLE executor;

-- changeset T9404:5


CREATE TABLE IF NOT EXISTS chat_room
(
    chat_room_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sender_id    UUID NOT NULL,
    receiver_id  UUID NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES users (user_id),
    FOREIGN KEY (receiver_id) REFERENCES users (user_id)
);

-- rollback DROP TABLE chat_room;

-- changeset T9404:6


CREATE TABLE IF NOT EXISTS message
(
    message_id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    chat_room_id UUID         NOT NULL,
    sender_id    UUID         NOT NULL,
    receiver_id  UUID         NOT NULL,
    content      VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL,
    FOREIGN KEY (chat_room_id) REFERENCES chat_room (chat_room_id),
    FOREIGN KEY (sender_id) REFERENCES users (user_id),
    FOREIGN KEY (receiver_id) REFERENCES users (user_id)
);

-- rollback DROP TABLE message;

-- changeset gordey_dovydenko:7

CREATE TABLE IF NOT EXISTS defect_status
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255),
    has_distance BOOLEAN DEFAULT FALSE
);

-- rollback DROP TABLE defect_status;

-- changeset gordey_dovydenko:8

CREATE TABLE IF NOT EXISTS application
(
    application_id     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    statement_id       UUID,
    longitude          DOUBLE PRECISION         NOT NULL,
    latitude           DOUBLE PRECISION         NOT NULL,
    application_status VARCHAR(50) CHECK ( application_status IN
                                           ('OPEN', 'REJECTED', 'IN_PROCESS', 'WAIT_ACCEPT', 'COMPLETED') ),
    defect_status_id   SERIAL                   NOT NULL,
    address            VARCHAR(255),
    created_time       TIMESTAMP WITH TIME ZONE NOT NULL,
    defect_distance    DOUBLE PRECISION,
    FOREIGN KEY (statement_id) REFERENCES statement (statement_id),
    FOREIGN KEY (defect_status_id) REFERENCES defect_status (id)
);

-- rollback DROP TABLE application;

-- changeset gordey_dovydenko:9

CREATE TABLE IF NOT EXISTS application_photo
(
    photo_id       VARCHAR(100) NOT NULL PRIMARY KEY,
    application_id UUID         NOT NULL,
    FOREIGN KEY (application_id) REFERENCES application (application_id)
);

-- rollback DROP TABLE application_photo;

-- changeset gordey_dovydenko:10

CREATE TABLE IF NOT EXISTS application_audio
(
    application_id UUID PRIMARY KEY,
    audio_id       VARCHAR(100) NOT NULL,
    FOREIGN KEY (application_id) REFERENCES application (application_id)
);

-- rollback DROP TABLE application_audio;

-- changeset gordey_dovydenko:11

CREATE INDEX IF NOT EXISTS idx_application_longitude_latitude ON application USING btree (longitude, latitude);
CREATE INDEX IF NOT EXISTS idx_application_process_status ON application USING hash (application_status);
CREATE INDEX IF NOT EXISTS idx_application_defect_status_id ON application USING hash (defect_status_id);

-- rollback DROP INDEX idx_application_longitude_latitude;
-- rollback DROP INDEX idx_application_process_status;
-- rollback DROP INDEX idx_application_defect_status_id;

-- changeset gordey_dovydenko:12
INSERT INTO defect_status (name, has_distance)
VALUES ('Стирание краски', TRUE),
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

-- changeset gordey_dovydenko:13

INSERT INTO organization (name, address, phone_number, email, organization_type)
VALUES ('ООО "Дорожное агентство"', 'г. Пермь, ул. Ленина, д. 1', '+7 (495) 123-45-67', 'agency1@agency.ru',
        'PERFORMER');

INSERT INTO users (organization_id, username, email, password, full_name, online_status)
VALUES ((SELECT organization_id FROM organization WHERE email = 'agency1@agency.ru'), 'agency1', 'bussiness1@gmail.com',
        '$2a$10$p/SE0M1upWg1Szb3eGLn0.t4lLC5hbOpmL.VuY0CpTQYmiP8kXG/W', 'agency1', FALSE);

INSERT INTO user_authority (user_id, authorities)
VALUES ((SELECT user_id FROM users WHERE email = 'bussiness1@gmail.com'), 'ROLE_ADMIN');



INSERT INTO organization (name, address, phone_number, email, organization_type)
VALUES ('ООО "Лучшее качество"', 'г. Пермь, ул. Ленина, д. 3', '+7 (495) 123-45-66', 'agency2@agency.ru', 'PERFORMER');

INSERT INTO users (organization_id, username, email, password, full_name, online_status)
VALUES ((SELECT organization_id FROM organization WHERE email = 'agency2@agency.ru'), 'agency2', 'bussiness2@gmail.com',
        '$2a$10$p/SE0M1upWg1Szb3eGLn0.t4lLC5hbOpmL.VuY0CpTQYmiP8kXG/W', 'agency2', FALSE);

INSERT INTO user_authority (user_id, authorities)
VALUES ((SELECT user_id FROM users WHERE email = 'bussiness2@gmail.com'), 'ROLE_ADMIN');



INSERT INTO organization (name, address, phone_number, email, organization_type)
VALUES ('Министерство транспорта', 'г. Пермь, ул. Ленина, д. 2', '+7 (495) 123-45-68', 'road1@perm.ru', 'CUSTOMER');

INSERT INTO users (organization_id, username, email, password, full_name, online_status)
VALUES ((SELECT organization_id FROM organization WHERE email = 'road1@perm.ru'), 'petr_ivanov', 'perm1@yandex.ru',
        '$2a$10$p/SE0M1upWg1Szb3eGLn0.t4lLC5hbOpmL.VuY0CpTQYmiP8kXG/W', 'Petr Ivanov', FALSE);

INSERT INTO user_authority (user_id, authorities)
VALUES ((SELECT user_id FROM users WHERE email = 'perm1@yandex.ru'), 'ROLE_ADMIN');



INSERT INTO organization (name, address, phone_number, email, organization_type)
VALUES ('ООО "Министерство улучшения инфраструктуры"', 'г. Пермь, ул. Ленина, д. 4', '+7 (495) 123-45-60',
        'road2@perm.ru', 'CUSTOMER');

INSERT INTO users (organization_id, username, email, password, full_name, online_status)
VALUES ((SELECT organization_id FROM organization WHERE email = 'road2@perm.ru'), 'andrey_fedorov', 'perm2@yandex.ru',
        '$2a$10$p/SE0M1upWg1Szb3eGLn0.t4lLC5hbOpmL.VuY0CpTQYmiP8kXG/W', 'Andrey Fedorov', FALSE);

INSERT INTO user_authority (user_id, authorities)
VALUES ((SELECT user_id FROM users WHERE email = 'perm2@yandex.ru'), 'ROLE_ADMIN');

-- rollback DELETE FROM user_authority WHERE user_id = (SELECT id FROM Users WHERE username = 'admin');
-- rollback DELETE FROM user WHERE username = 'admin';
-- rollback DELETE FROM organization WHERE email = 'road@road.ru';


-- changeset gordey_dovydenko:14

CREATE TABLE voice_scheduler
(
    application_id UUID PRIMARY KEY,
    process_id VARCHAR(60) NOT NULL,
    schedule_time   TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(50) CHECK ( status IN ('IN_PROCESS', 'EXECUTED') ),
    FOREIGN KEY (application_id) REFERENCES statement (statement_id)
);

-- rollback DROP TABLE voice_scheduler;
