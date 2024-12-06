-- liquibase formatted sql

-- changeset Imaltcev:1
CREATE TABLE product_data (
    id SERIAL PRIMARY KEY,
    product_name text,
    product_id text,
    product_text text
);

-- changeset Imaltcev:2
CREATE type QUERY as enum (
    'USER_OF',
    'ACTIVE_USER_OF',
    'TRANSACTION_SUM_COMPARE',
    'TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW'
);

-- changeset Imaltcev:3
CREATE TABLE rule (
    id SERIAL,
    query Query,
    arguments text[],
    negate BOOLEAN,
    data_id Integer references product_data (id) ON DELETE CASCADE
);

-- changeset Imaltcev:4
CREATE TABLE stats (
    id SERIAL,
    rule_id text,
    count Integer
);
