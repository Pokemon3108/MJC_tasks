CREATE TABLE gift_certificate
(
    id               INTEGER AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255),
    description      TEXT,
    price            INT,
    duration         INT,
    create_date      DATE,
    last_update_date DATE
);

CREATE TABLE tag
(
    id   INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
)