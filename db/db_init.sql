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
	`games` INT NULL,
	PRIMARY KEY(`id`)
) ENGINE = InnoDB;



USE `realrank`;
DROP TABLE IF EXISTS `battle`;

-- Table `battle`
CREATE TABLE `battle` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`challenger` VARCHAR(32) NOT NULL,
	`champion` VARCHAR(32) NOT NULL,
	`req_time` DATETIME NOT NULL,
	`acc_time` DATETIME NULL,
	`state` TINYINT NULL,
	`winner` VARCHAR(32) NULL,
	PRIMARY KEY(`id`)
) ENGINE = InnoDB;

USE `realrank`;
DROP TABLE IF EXISTS `score`;

-- Table `score`
CREATE TABLE `score` (
	`id` VARCHAR(32) NOT NULL,
	`score` INT NOT NULL,
	`reputation` INTEGER NOT NULL DEFAULT 100,
	PRIMARY KEY(`id`)
) ENGINE = InnoDB;


DROP TABLE IF EXISTS `realrank`.`daily_score`;

CREATE TABLE `realrank`.`daily_score` (rank INT AUTO_INCREMENT, id VARCHAR(32) NOT NULL, score int(11) NOT NULL, reputation int(11) NOT NULL DEFAULT 100, PRIMARY KEY(rank));


DROP TABLE IF EXISTS `realrank`.`weekly_score`;

CREATE TABLE `realrank`.`weekly_score` (rank INT AUTO_INCREMENT, id VARCHAR(32) NOT NULL, score int(11) NOT NULL, reputation int(11) NOT NULL DEFAULT 100, PRIMARY KEY(rank));


DROP TABLE IF EXISTS `realrank`.`monthly_score`;

CREATE TABLE `realrank`.`monthly_score` (rank INT AUTO_INCREMENT, id VARCHAR(32) NOT NULL, score int(11) NOT NULL, reputation int(11) NOT NULL DEFAULT 100, PRIMARY KEY(rank));
