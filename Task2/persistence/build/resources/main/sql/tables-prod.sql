CREATE TABLE gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255),
    description      TEXT,
    price            DECIMAL,
    duration         INT,
    create_date      TIMESTAMP,
    last_update_date TIMESTAMP
);

CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE gift_certificate_tag
(
    id             SERIAL PRIMARY KEY,
    certificate_id INTEGER REFERENCES gift_certificate (id),
    tag_id         INTEGER REFERENCES tag (id)
)