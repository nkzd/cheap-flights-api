INSERT INTO role (id, name)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name)
VALUES (2, 'ROLE_REGULAR_USER');

-- admin/admin
INSERT INTO user (id, first_name, last_name, username, password, role_id)
VALUES (1, 'admin', 'admin', 'admin', '$2a$10$2QnpM6kzuiwg/C0a1KJBEeq.dtWPa1Gf83XltsEAuOqSwA2Divsq.',1);

--user/user
INSERT INTO user (id, first_name, last_name, username, password, role_id)
VALUES (2, 'user', 'user', 'user', '$2a$10$u3N.hygkwMzw29/cnAy54.HFTVX4gw3Q4Uix/UYUvWTSZK1d9iNza',2);

INSERT INTO city (id, country, description, name)
VALUES (1, 'Canada', 'test', 'Toronto');
INSERT INTO city (id, country, description, name)
VALUES (2, 'Papua New Guinea', 'test', 'Goroka');
INSERT INTO city (id, country, description, name)
VALUES (3, 'United States', 'test', 'New York');
INSERT INTO city (id, country, description, name)
VALUES (4, 'United States', 'test', 'Miami');
INSERT INTO city (id, country, description, name)
VALUES (5, 'United States', 'test', 'Portland');
INSERT INTO city (id, country, description, name)
VALUES (6, 'United States', 'test', 'Sacramento');
INSERT INTO city (id, country, description, name)
VALUES (7, 'United States', 'test', 'Philadelphia');
INSERT INTO city (id, country, description, name)
VALUES (8, 'France', 'test', 'Paris');
INSERT INTO city (id, country, description, name)
VALUES (9, 'United Kingdom', 'test', 'London');
INSERT INTO city (id, country, description, name)
VALUES (10, 'Germany', 'test', 'Berlin');
INSERT INTO city (id, country, description, name)
VALUES (11, 'Sweden', 'test', 'Stockholm');
INSERT INTO city (id, country, description, name)
VALUES (12, 'Spain', 'test', 'Barcelona');

INSERT INTO comment (id, description, user_id, city_id)
VALUES (1, 'desc', 1, 1);
INSERT INTO comment (id, description, user_id, city_id)
VALUES (2, 'desc1', 1, 1);
INSERT INTO comment (id, description, user_id, city_id)
VALUES (3, 'desc2', 1, 1);

INSERT INTO comment (id, description, user_id, city_id)
VALUES (4, 'desc', 1, 2);
INSERT INTO comment (id, description, user_id, city_id)
VALUES (5, 'desc1', 1, 2);
INSERT INTO comment (id, description, user_id, city_id)
VALUES (6, 'desc2', 1, 2);