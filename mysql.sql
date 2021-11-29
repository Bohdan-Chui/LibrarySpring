CREATE DATABASE IF NOT EXISTS library;
USE  library;

CREATE TABLE IF NOT EXISTS Role(
                                   id INT AUTO_INCREMENT,
                                   role_id  VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS User
(
    id INT AUTO_INCREMENT,
    patronymic VARCHAR(50) NOT NULL,
    firstname VARCHAR(50)  NOT NULL,
    secondname VARCHAR(50)  NOT NULL,
    password VARCHAR(50)  NOT NULL,
    email VARCHAR(50) UNIQUE DEFAULT 'Unnamed',
    id_role INT NOT NULL,
    blocked BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT rolefk
    FOREIGN KEY(id_role) REFERENCES Role(id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS Book
(
    Id INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    publisher VARCHAR(50) NOT NULL,
    count INT DEFAULT NULL,
    publishedTime DATE NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS Card
(
    id INT AUTO_INCREMENT,
    bookId INT,
    userId INT,
    place VARCHAR(20) DEFAULT 'library',
    status VARCHAR(20) DEFAULT 'not confirmed',
    returnDate DATE NOT NULL,
    fine INT,
    PRIMARY KEY(id),
    CONSTRAINT UserFK
    FOREIGN KEY (UserId) REFERENCES User(Id) ON DELETE CASCADE,
    CONSTRAINT BookFK
    FOREIGN KEY (BookId) REFERENCES Book(Id) ON DELETE CASCADE
    );