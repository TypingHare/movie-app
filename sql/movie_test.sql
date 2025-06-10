-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS movie_test;

-- Use the created database
USE movie;

-- Create the movies table
CREATE TABLE IF NOT EXISTS movies (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  year INT,
  length INT CHECK (length > 0),
  genre VARCHAR(20),
  PRIMARY KEY (id)
);

-- Insert movie records
INSERT INTO movies (title, year, length, genre) 
VALUES ('Gone With the Wind', 1939, 231, 'drama');

INSERT INTO movies (title, year, length, genre) 
VALUES ('Star Wars', 1977, 124, 'sciFi');
