DROP DATABASE IF EXISTS java_hw_supermarket;
CREATE DATABASE IF NOT EXISTS java_hw_supermarket;

USE java_hw_supermarket;


CREATE TABLE IF NOT EXISTS products (
                                        id INT AUTO_INCREMENT NOT NULL,
                                        name VARCHAR(100) NOT NULL,
    measurement VARCHAR(10) NOT NULL,
    quantityAvailable INTEGER,
    cost DOUBLE NOT NULL,
    sellingPrice DOUBLE NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS customers (
                                         id INT AUTO_INCREMENT NOT NULL,
                                         customerName VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(100) NOT NULL,
    balance DOUBLE,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS salesManagers (
                                             id INT AUTO_INCREMENT NOT NULL,
                                             salesName VARCHAR(100),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS shopOwners (
                                          id INT AUTO_INCREMENT NOT NULL,
                                          ownerName VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS sales (
                                     id INT AUTO_INCREMENT NOT NULL,
                                     customerEmail VARCHAR(255),
    productName VARCHAR(100),
    quantitySold INTEGER,
    productCost DOUBLE,
    productPrice DOUBLE,
    totalSales DOUBLE,
    PRIMARY KEY(id)
    );

INSERT INTO shopOwners(ownerName, email, password)
VALUES ("Jack", "jack@shop.com", "shop");

INSERT INTO products(name, measurement, quantityAvailable, cost, sellingPrice)
VALUES
    ("Milk", "pc", 100, 0.80, 1.20),
    ("Bread", "pc", 50, 0.90, 1.50),
    ("Potatoes", "kg", 200, 0.20, 0.50),
    ("Bananas", "kg", 100, 1.00, 1.80),
    ("Shampoo", "pc", 75, 1.50, 2.90),
    ("Chicken", "kg", 48, 2.50, 3.60),
    ("Tea", "pc", 100, 0.80, 1.20),
    ("Rice", "pc", 241, 2.50, 3.99),
    ("Napkins", "pc", 99, 0.90, 1.99),
    ("Pen", "pc", 300, 0.80, 1.35),
    ("Computer mouse", "pc", 25, 20, 34.99);

SELECT * FROM products;
SELECT * FROM customers;
SELECT * FROM salesManagers;
SELECT * FROM shopOwners;
SELECT * FROM sales;
