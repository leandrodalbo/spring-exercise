DROP TABLE IF EXISTS company;
CREATE TABLE company (
    company_id          BIGSERIAL PRIMARY KEY NOT NULL,
    version             integer NOT NULL,
    company_number      varchar(255),
    company_type        varchar(255),
    title               varchar(255),
    company_status      varchar(255),
    date_of_creation      varchar(50)
);

DROP TABLE IF EXISTS officer;
CREATE TABLE officer (
    officer_id          BIGSERIAL PRIMARY KEY NOT NULL,
    version             integer NOT NULL,
    name                varchar(255),
    officer_role        varchar(255),
    appointed_on        varchar(255),
    company_id          BIGSERIAL
);

DROP TABLE IF EXISTS address;
CREATE TABLE address (
    address_id          BIGSERIAL PRIMARY KEY NOT NULL,
    version             integer NOT NULL,
    locality            varchar(255),
    postal_code         varchar(50),
    premises            varchar(255),
    address_line_1      varchar(255),
    country             varchar(100),
    company_id          BIGINT,
    officer_id          BIGINT
);