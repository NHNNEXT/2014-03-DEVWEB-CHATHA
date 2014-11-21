-- ----------------------------------------------------------------
-- Table Setup
-- ----------------------------------------------------------------
USE `realrank`;

DELETE FROM `user`;
INSERT INTO `user` VALUES (
	'test', 'test@example.com', 'asdf', 'Kim Person', 'M', '1989-04-05'
);
