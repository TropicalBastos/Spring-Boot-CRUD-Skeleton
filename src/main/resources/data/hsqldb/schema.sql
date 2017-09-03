DROP TABLE IF EXISTS User;

CREATE TABLE User(
	user_id INT AUTO_INCREMENT NOT NULL,
	name VARCHAR(250) NOT NULL,
	email VARCHAR(250) NOT NULL,
	password VARCHAR(250) NOT NULL,
	PRIMARY KEY(user_id)
);

DROP TABLE IF EXISTS Role;

CREATE TABLE Role(
	role_id INT AUTO_INCREMENT NOT NULL,
	role VARCHAR(250) NOT NULL,
	PRIMARY KEY(role_id)
);

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;