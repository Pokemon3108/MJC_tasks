CREATE TABLE gift_certificate
(
    id               INTEGER AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255) UNIQUE,
    description      TEXT,
    price            DECIMAL,
    duration         INT,
    create_date      TIMESTAMP,
    last_update_date TIMESTAMP
);

CREATE TABLE tag
(
    id   INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE gift_certificate_tag
(
    id             INTEGER AUTO_INCREMENT PRIMARY KEY,
    certificate_id INTEGER REFERENCES gift_certificate (id),
    tag_id         INTEGER REFERENCES tag (id)
)