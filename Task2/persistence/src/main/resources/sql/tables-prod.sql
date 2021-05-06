CREATE TABLE gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255) UNIQUE,
    description      TEXT,
    price            DECIMAL,
    duration         INT,
    create_date      TIMESTAMP,
    last_update_date TIMESTAMP
);

CREATE TABLE tag
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE gift_certificate_tag
(
    id             SERIAL PRIMARY KEY,
    certificate_id INTEGER REFERENCES gift_certificate (id),
    tag_id         INTEGER REFERENCES tag (id)
);

CREATE TABLE usr
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE order
(
    id             SERIAL PRIMARY KEY,
    user_id        BIGINT    NOT NULL REFERENCES usr (id),
    certificate_id BIGINT    NOT NULL REFERENCES gift_certificate (id),
    cost           DECIMAL   NOT NULL,
    purchase_date  TIMESTAMP NOT NULL
);


-- for auditing
CREATE TABLE revinfo
(
    rev INTEGER NOT NULL,
    revtstmp BIGINT,
    CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
);

CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 50;

CREATE TABLE tag_aud
(
    id SERIAL NOT NULL,
    rev INTEGER NOT NULL,
    revtype SMALLINT,
    name VARCHAR(255),
    CONSTRAINT tag_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT tag_aud_revinfo FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE gift_certificate_aud
(
    id               SERIAL NOT NULL,
    name             VARCHAR(255),
    description      TEXT,
    price            DECIMAL,
    duration         INT,
    create_date      TIMESTAMP,
    last_update_date TIMESTAMP,
    rev INTEGER NOT NULL,
    revtype SMALLINT,
    CONSTRAINT gift_certificate_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT gift_certificate_aud_revinfo FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE gift_certificate_tag_aud
(
    id             SERIAL NOT NULL,
    certificate_id INTEGER REFERENCES gift_certificate (id),
    tag_id         INTEGER REFERENCES tag (id),
    rev INTEGER NOT NULL,
    revtype SMALLINT,
    CONSTRAINT gift_certificate_tag_aud_pkey PRIMARY KEY (id, rev),
    CONSTRAINT gift_certificate_tag_aud_revinfo FOREIGN KEY (rev)
        REFERENCES revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
);