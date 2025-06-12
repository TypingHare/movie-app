-- Create the database if it doesn't exist
DROP DATABASE IF EXISTS movie_test;
CREATE DATABASE movie_test;

-- Use the created database
USE movie_test;

-- Create the movies table
CREATE TABLE movies (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    year INT,
    length INT CHECK (length > 0),
    genre VARCHAR(20),
    PRIMARY KEY (id)
);

-- Create the actors table
CREATE TABLE IF NOT EXISTS actors (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    birth_year INT,
    PRIMARY KEY (id)
);

-- Create the acts table (many-to-many relationship)
CREATE TABLE IF NOT EXISTS acts (
    actor_id INT NOT NULL,
    movie_id INT NOT NULL,
    role VARCHAR(100),
    PRIMARY KEY (actor_id, movie_id),
    FOREIGN KEY (actor_id) REFERENCES actors (id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies (id) ON DELETE CASCADE
);

-- Insert movie records
INSERT INTO movies (title, year, length, genre)
VALUES ('Gone With the Wind', 1939, 231, 'drama');

INSERT INTO movies (title, year, length, genre)
VALUES ('Star Wars', 1977, 124, 'sciFi');

INSERT INTO movies (title, year, length, genre)
VALUES ('Return of the Jedi', 1983, 131, 'sciFi');

-- Insert actor records
INSERT INTO actors (name, birth_year)
VALUES ('Vivien Leigh', 1913);

INSERT INTO actors (name, birth_year)
VALUES ('Clark Gable', 1901);

INSERT INTO actors (name, birth_year)
VALUES ('Mark Hamill', 1951);

-- Insert acts records
INSERT INTO acts (actor_id, movie_id, role)
VALUES (1, 1, 'Scarlett O''Hara');

INSERT INTO acts (actor_id, movie_id, role)
VALUES (2, 1, 'Rhett Butler');

INSERT INTO acts (actor_id, movie_id, role)
VALUES (3, 2, 'Luke Skywalker');

INSERT INTO acts (actor_id, movie_id, role)
VALUES (3, 3, 'Luke Skywalker');
