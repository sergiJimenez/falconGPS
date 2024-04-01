DROP DATABASE falcongps;
CREATE DATABASE falcongps;
USE falcongps;

CREATE TABLE UserLinked1 (
    firstName VARCHAR(25) NOT NULL,
    dni CHAR(9) PRIMARY KEY,
    phoneNumber CHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL
);

CREATE TABLE UserLinked2 (
    firstName VARCHAR(25) NOT NULL,
    dni CHAR(9) PRIMARY KEY,
    phoneNumber CHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL
);

CREATE TABLE UserLinked3 (
    firstName VARCHAR(25) NOT NULL,
    dni CHAR(9) PRIMARY KEY,
    phoneNumber CHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL
);

CREATE TABLE UserLinked4 (
    firstName VARCHAR(25) NOT NULL,
    dni CHAR(9) PRIMARY KEY,
    phoneNumber CHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL
);

CREATE TABLE Vehicle (
    motorcyclePlate CHAR (7) PRIMARY KEY,
    dniOwner char(9) NOT NULL
);

CREATE TABLE MainUser (
    firstName VARCHAR(25) NOT NULL,
    secondName VARCHAR(25) NOT NULL,
    surname VARCHAR(25) NOT NULL,
    dni CHAR(9) PRIMARY KEY,
    phoneNumber CHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    UserLinked1 CHAR(9) NOT NULL,
    UserLinked2 CHAR(9),
    UserLinked3 CHAR(9),
    UserLinked4 CHAR(9),
	ID_Device VARCHAR(250),
    FOREIGN KEY (UserLinked1) REFERENCES UserLinked1 (dni),
    FOREIGN KEY (UserLinked2) REFERENCES UserLinked2 (dni),
    FOREIGN KEY (UserLinked3) REFERENCES UserLinked3 (dni),
    FOREIGN KEY (UserLinked4) REFERENCES UserLinked4 (dni)
);

INSERT INTO UserLinked1 VALUES (
    "User1FirstName", "12345678A", "123456789", "user1@example.com", "User1Address"
);

INSERT INTO UserLinked2 VALUES (
    "User2FirstName", "12345678B", "987654321", "user2@example.com", "User2Address"
);