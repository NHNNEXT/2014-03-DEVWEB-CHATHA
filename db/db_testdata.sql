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
	('champ', 1880, 67), 
	('champ2', 2100, 110),
	('neocjmix', 2200, 99),
	('Chanjin', 1900, 64),
	('dooyoo', 1850, 88),
	('samyuk', 2330, 101),
	('champ6', 2200, 66),
	('qwerg', 2400, 110),
	('gosohan', 1780, 120),
	('chp', 2650, 100),
	('amp', 1820, 82),
	('chal', 2310, 110);

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
	(NULL, 'champ', 'chal', '2015-12-09 09:00:00', NULL, 0, NULL);


INSERT INTO `daily_score` (id, score, reputation) VALUES
	('chp', 2650, 100),
	('champ2', 2500, 110),
	('Chanjin', 2400, 64),
	('dooyoo', 2350, 88),
	('champ6', 2200, 66),
	('qwerg', 2100, 110),
	('neocjmix', 2000, 99),
	('amp', 1980, 82),
	('samyuk', 1900, 101),
	('gosohan', 1780, 120),
	('chal', 1710, 110),
	('champ', 1650, 67);

INSERT INTO `weekly_score` (id, score, reputation) VALUES
	('Chanjin', 2700, 64),
	('champ2', 2600, 110),
	('gosohan', 2580, 120),
	('dooyoo', 2350, 88),
	('qwerg', 2200, 110),
	('neocjmix', 2110, 99),
	('chp', 2090, 100),
	('amp', 2000, 82),
	('champ', 1950, 67),
	('samyuk', 1900, 101),
	('chal', 1810, 110),
	('champ6', 1800, 66);

INSERT INTO `monthly_score` (id, score, reputation) VALUES
	('gosohan', 2880, 120),
	('champ', 2750, 67),
	('dooyoo', 2500, 88),
	('qwerg', 2400, 110),
	('chal', 2310, 110),
	('champ2', 2100, 110),
	('neocjmix', 2010, 99),
	('Chanjin', 1970, 64),
	('chp', 1890, 100),
	('champ6', 1800, 66),
	('amp', 1770, 82),
	('samyuk', 1700, 101);
