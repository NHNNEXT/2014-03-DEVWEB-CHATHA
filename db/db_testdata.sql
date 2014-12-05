-- ----------------------------------------------------------------
-- Table Setup
-- ----------------------------------------------------------------
USE `realrank`;

DELETE FROM `user`;
INSERT INTO `user` VALUES (
	'champ', 'champ@example.com', 'asdf', 'champion', 'M', '1990-05-05'
);
INSERT INTO `user` VALUES (
	'chal', 'chal@example.com', 'asdf', 'challenger', 'F', '1988-04-07'
);

INSERT INTO `score` VALUES (
	'champ', 0
);
INSERT INTO `score` VALUES (
	'chal', 0
);