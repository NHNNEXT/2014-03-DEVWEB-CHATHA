-- ----------------------------------------------------------------
-- DB and User Setup
-- ----------------------------------------------------------------
DROP DATABASE IF EXISTS `realrank`;
CREATE DATABASE `realrank` DEFAULT CHARACTER SET utf8;

USE `mysql`;
DELETE FROM `user` WHERE User='realrank_test';
DELETE FROM `db` WHERE User='realrank_test';
FLUSH PRIVILEGES;

CREATE USER 'realrank_test'@'%' IDENTIFIED BY '1234';
CREATE USER 'realrank_test'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON `realrank`.* TO 'realrank_test'@'%';
GRANT ALL PRIVILEGES ON `realrank`.* TO 'realrank_test'@'localhost';
FLUSH PRIVILEGES;


-- ----------------------------------------------------------------
-- Table Setup
-- ----------------------------------------------------------------
USE `realrank`;
DROP TABLE IF EXISTS `user`;

-- Table `user`
CREATE TABLE `user` (
	`id` VARCHAR(32) NOT NULL,
	`email` VARCHAR(32) NOT NULL,
	`password` VARCHAR(64) NULL,
	`nickname` VARCHAR(64) NULL,
	`gender` CHAR(1) NULL,
	`birthday` DATE NOT NULL,
	PRIMARY KEY(`id`)
) ENGINE = InnoDB;

