INSERT INTO USERS(username, password) VALUES('user', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'); --password
INSERT INTO USERS(username, password) VALUES('admin', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'); --password
INSERT INTO USERS(username, password) VALUES('read_only', '$2a$10$xszlj1pbFnz6kKreb3X9pugjNUg0MAcC1ff/NR43oKNthJYYcCRj6'); --password

INSERT INTO ROLES(name) VALUES('USER');
INSERT INTO ROLES(name) VALUES('ADMIN');
INSERT INTO ROLES(name) VALUES('READ_ONLY');

INSERT INTO ROLE_USER(user_id, role_id) VALUES(1, 1);
INSERT INTO ROLE_USER(user_id, role_id) VALUES(2, 1);
INSERT INTO ROLE_USER(user_id, role_id) VALUES(2, 2);
INSERT INTO ROLE_USER(user_id, role_id) VALUES(3, 3);

INSERT INTO mobile_categories (name) VALUES ('Smartphones');
INSERT INTO mobile_categories (name) VALUES ('Tablets');
INSERT INTO mobile_categories (name) VALUES ('Feature Phones');
INSERT INTO mobile_categories (name) VALUES ('Gaming Phones');
INSERT INTO mobile_categories (name) VALUES ('Foldable Phones');

INSERT INTO mobiles (name, brand, price, category_id) VALUES ('iPhone 13', 'Apple', 999.99, 1);
INSERT INTO mobiles (name, brand, price, category_id) VALUES ('Galaxy S21', 'Samsung', 799.99, 1);
INSERT INTO mobiles (name, brand, price, category_id) VALUES ('iPad Pro', 'Apple', 999.99, 2);
INSERT INTO mobiles (name, brand, price, category_id) VALUES ('Galaxy Tab S7', 'Samsung', 649.99, 2);
INSERT INTO mobiles (name, brand, price, category_id) VALUES ('Nokia 3310', 'Nokia', 59.99, 3);
INSERT INTO mobiles (name, brand, price, category_id) VALUES ('ROG Phone 5', 'ASUS', 999.99, 4);
INSERT INTO mobiles (name, brand, price, category_id) VALUES ('Galaxy Z Fold 3', 'Samsung', 1799.99, 5);