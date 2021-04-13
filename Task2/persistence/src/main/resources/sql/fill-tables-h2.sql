INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'certificatto', 'description', 12.34, 12, '2021-04-11', '2021-04-11');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (2, 'new certificate', 'sales certificate', 1100, 89, '2021-04-11', '2021-04-11');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (3, 'new apple', 'fruits sales', 1234, 45, '2020-12-11', '2021-01-03');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (4, 'happy birthday', 'celebrate your main day', 446, 16, '2017-08-22', '2017-09-18');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (5, 'new car', 'buy your new transport', 90, 99, '2009-03-09', '2009-04-11');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (6, 'happy weekday', 'enjoy yourself', 1, 100, '2015-07-11', '2015-08-31');


INSERT INTO tag (id, name) VALUES (1, 'nature');
INSERT INTO tag (id, name) VALUES (2, 'party');
INSERT INTO tag (id, name) VALUES (3, 'car');
INSERT INTO tag (id, name) VALUES (4, 'enjoyable');


INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(1, 1);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(1, 3);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(2, 2);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(2, 4);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(3, 1);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(3, 4);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(4, 2);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(4, 4);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(5, 3);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(5, 4);
INSERT INTO gift_certificate_tag  (certificate_id, tag_id) VALUES(6, 1);