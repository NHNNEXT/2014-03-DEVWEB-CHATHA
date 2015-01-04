-- ----------------------------------------------------------------
-- Table Setup
-- ----------------------------------------------------------------
USE `realrank`;

DELETE FROM `user`;
INSERT INTO `user` VALUES
	('champ', 'champ1234asdf@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('champ2', 'chal1234asdf@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'challenger', 'F', '1988-04-07', 30),
	('neocjmix', 'neocjmix@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('Chanjin', 'cjmixx@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('dooyoo', 'samyuk@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('samyuk', 'champ1234asdf@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('champ6', 'cha4asdf@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('qwerg', 'dooyoo234@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('gosohan', 'csadf4asdf@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('chp', 'cqwer@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('amp', 'gosohan@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'champion', 'M', '1990-05-05', 100),
	('chal', 'chal1234asdf@gmail.com', HEX(AES_ENCRYPT('asdf', 'asdf')), 'challenger', 'F', '1988-04-07', 30);

DELETE FROM `score`;
INSERT INTO `score` VALUES
	('champ', 100, 99),
	('chal', 10, 88);

-- state field: 0(acceptable and not validated) 1(accepted) 2(outdated) 3(canceled) 4(denied)
DELETE FROM `battle`;
INSERT INTO `battle` VALUES
	(NULL, 'chal', 'champ', '2014-12-09 09:00:00', NULL, 1, NULL),
	(NULL, 'champ', 'chal', '2014-12-09 09:00:00', NULL, 1, NULL),
	(NULL, 'chal', 'champ', '2014-12-09 10:00:00', NULL, 2, NULL),
	(NULL, 'champ', 'chal', '2014-12-09 10:00:00', NULL, 2, NULL),
	(NULL, 'chal', 'champ', '2014-12-09 11:00:00', NULL, 3, NULL),
	(NULL, 'champ', 'chal', '2014-12-09 11:00:00', NULL, 3, NULL),
	(NULL, 'chal', 'champ', '2014-12-09 12:00:00', NULL, 4, NULL),
	(NULL, 'champ', 'chal', '2014-12-09 12:00:00', NULL, 4, NULL),
	(NULL, 'chal', 'champ', '2015-12-09 09:00:00', NULL, 0, NULL),
	(NULL, 'chal', 'champ', '2015-12-10 09:00:00', NULL, 0, NULL),
	(NULL, 'champ', 'chal', '2015-12-09 09:00:00', NULL, 0, NULL),
	(NULL, 'champ', 'chal', '2015-12-10 09:00:00', NULL, 0, NULL);
