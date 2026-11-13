CREATE DATABASE IF NOT EXISTS awd_practice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE awd_practice;
CREATE TABLE IF NOT EXISTS products (
 id INT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(100) NOT NULL,
 price DECIMAL(10,2) NOT NULL,
 CONSTRAINT chk_price_nonnegative CHECK(price >= 0)
);
INSERT INTO products(name,price)
SELECT 'Keyboard',250.00 WHERE NOT EXISTS (SELECT 1 FROM products WHERE name='Keyboard');
INSERT INTO products(name,price)
SELECT 'Mouse',120.00 WHERE NOT EXISTS (SELECT 1 FROM products WHERE name='Mouse');