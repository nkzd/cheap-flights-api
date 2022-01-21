DROP TABLE IF EXISTS comment cascade;
DROP TABLE IF EXISTS city cascade;
DROP TABLE IF EXISTS route cascade;
DROP TABLE IF EXISTS airport cascade;
DROP TABLE IF EXISTS user cascade;
DROP TABLE IF EXISTS role cascade;
DROP SEQUENCE IF EXISTS HIBERNATE_SEQUENCE;

CREATE TABLE role
(
    id   INT AUTO_INCREMENT,
    name VARCHAR NOT NULL,
    CONSTRAINT ROLES_PK
        PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         INT AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    username   VARCHAR(100) NOT NULL,
    password   TEXT,
    role_id    INT          NOT NULL,
--     not needed due bcrypt usage salt     VARCHAR NOT NULL,
    CONSTRAINT USERS_PK
        PRIMARY KEY (id),
    CONSTRAINT USER_ROLE_ID_FK
        FOREIGN KEY (role_id) REFERENCES role (id)
);



CREATE TABLE city
(
    id          INT AUTO_INCREMENT,
    country     VARCHAR(100)  NOT NULL,
    description VARCHAR(1000) NOT NULL,
    name        VARCHAR(100)  NOT NULL,
    CONSTRAINT CITY_PK
        PRIMARY KEY (id),
    CONSTRAINT CITY_NAME_COUNTRY_UINDEX UNIQUE (name, country)
);

CREATE TABLE comment
(
    id          INT AUTO_INCREMENT,
    description TEXT     NOT NULL,
    user_id     INT      NOT NULL,
    city_id     INT      NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT now(),
    updated_at  DATETIME,
    CONSTRAINT COMMENT_PK
        PRIMARY KEY (id),
    CONSTRAINT COMMENT_USER_ID_FK
        FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT COMMENT_CITY_ID_FK
        FOREIGN KEY (city_id) REFERENCES city (id)
);

CREATE TABLE airport
(
--     id          INT AUTO_INCREMENT,
    airport_id  INT                                NOT NULL,
    name        VARCHAR(100)                       NOT NULL,
    city_id     INT                                NOT NULL,
    country     VARCHAR(100)                       NOT NULL,
    iata        VARCHAR(3),
    icao        VARCHAR(4),
    latitude    DECIMAL(12, 6)                     NOT NULL,
    longitude   DECIMAL(12, 6)                     NOT NULL,
    altitude    INT,
    timezone    DECIMAL(3, 1),
    dst         ENUM ('e','a','s','o','z','n','u') NOT NULL,
    tz          VARCHAR(100),
    type        VARCHAR(100)                       NOT NULL,
    data_source VARCHAR(100)                       NOT NULL,

    CONSTRAINT AIRPORT_PK
        PRIMARY KEY (airport_id),

    CONSTRAINT AIRPORT_CITY_FK
        FOREIGN KEY (city_id)
            REFERENCES city (id)
);

CREATE TABLE route
(
    id                     INT AUTO_INCREMENT NOT NULL,
    airline_code           VARCHAR(3)         NOT NULL,
    airline_id             INT                NOT NULL,
    source_airport_id      INT                NOT NULL,
    destination_airport_id INT                NOT NULL,
    code_share             BOOLEAN,
    stops                  INT                NOT NULL,
    equipment              VARCHAR(100)       NOT NULL,
    price                  DECIMAL(6, 3)      NOT NULL,


    CONSTRAINT route_pk
        PRIMARY KEY (id),

    CONSTRAINT route_source_airport_fk
        FOREIGN KEY (source_airport_id)
            REFERENCES airport (airport_id),

    CONSTRAINT route_destination_airport_fk
        FOREIGN KEY (destination_airport_id)
            REFERENCES airport (airport_id)
);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;

