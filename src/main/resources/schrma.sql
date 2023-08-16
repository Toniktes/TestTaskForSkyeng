CREATE TABLE IF NOT EXISTS postal_item(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    type VARCHAR(254) NOT NULL,
    addresseeIndex BIGINT NOT NULL,
    addresseeAddress VARCHAR(254) NOT NULL,
    addresseeName VARCHAR(254) NOT NULL,
    status VARCHAR(254) NOT NULL
    );

CREATE TABLE IF NOT EXISTS post_office(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    index BIGINT NOT NULL,
    name VARCHAR(254) NOT NULL,
    addresseeName VARCHAR(254) NOT NULL
    );

CREATE TABLE IF NOT EXISTS history(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    item_id BIGINT NOT NULL,
    post_office_id integer[]
    );
