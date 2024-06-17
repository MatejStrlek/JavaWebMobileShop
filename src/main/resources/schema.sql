CREATE TABLE IF NOT EXISTS USERS(
                      id  INT GENERATED ALWAYS AS IDENTITY,
                      username VARCHAR(30) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS ROLES(
                      id  INT GENERATED ALWAYS AS IDENTITY,
                      name VARCHAR(15) NOT NULL,
                      PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS ROLE_USER(
                          user_id INT NOT NULL,
                          role_id INT NOT NULL,
                          PRIMARY KEY(user_id, role_id),
                          FOREIGN KEY(user_id) REFERENCES USERS(id),
                          FOREIGN KEY(role_id) REFERENCES ROLES(id)
);

CREATE TABLE IF NOT EXISTS mobile_categories (
                                   id INT GENERATED ALWAYS AS IDENTITY,
                                   name VARCHAR(255) NOT NULL,
                                   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS mobiles (
                         id INT GENERATED ALWAYS AS IDENTITY,
                         name VARCHAR(255) NOT NULL,
                         brand VARCHAR(255) NOT NULL,
                         price DOUBLE NOT NULL,
                         category_id BIGINT,
                         FOREIGN KEY (category_id) REFERENCES mobile_categories(id),
                         PRIMARY KEY(id)
);