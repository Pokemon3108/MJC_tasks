CREATE TABLE gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255),
    description      TEXT,
    price            INT,
    duration         INT,
    create_date      DATE,
    last_update_date DATE
);

CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE gift_certificate_tag
(
    id SERIAL PRIMARY KEY,
    certificate_id INTEGER REFERENCES gift_certificate(id),
    tag_id INTEGER REFERENCES tag(id)
)