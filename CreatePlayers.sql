DROP DATABASE IF EXISTS `user_database`;
CREATE DATABASE `user_database`;
USE `user_database`;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `player_name` varchar(50) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `salary` int NOT NULL,
  `manager_name` varchar(20) NOT NULL,
  `coach_name` varchar(20) NOT NULL,
    `city` varchar(20) NOT NULL,

  PRIMARY KEY  (`player_name`)
  );

INSERT INTO user VALUES ("John", "moons", 15000, "ben", "sam,", "atlanta"),
  			("Eoin", "rockets", 25000, "john", "oliver,", "san fracnsio"),
  			("liam", "spices", 35000, "ben", "sam,", "philly"),
            ("clinton", "ice", 5000, "rita", "sammy,", "la"),
            ("allen", "lakers", 15000, "luke", "sam,", "la"),
             ("barry", "warriors", 25000, "benson", "jamal,", "golden state"),
              ("demar", "raptors", 20000, "leon", "samuel,", "toronto"),
               ("lia", "clippers", 65000, "ella", "rio,", "charlotte"),
                ("rhia", "zooms", 15000, "lia", "clinton,", "cleveland"),
                 ("sam", "heat", 10000, "elsa", "ronaldo,", "chicagi");

