-- Adminer 4.8.1 MySQL 10.11.3-MariaDB dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS `maven`;
CREATE DATABASE `maven` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `maven`;

DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

TRUNCATE `tasks`;
INSERT INTO `tasks` (`id`, `name`, `description`) VALUES
(1,	'Test Case',	'Test Case Task Manager'),
(6,	'Lorem Ipsum',	'Lorem Ipsum dolor sit amet'),
(7,	'Hello World',	'Watch Hello World Anime');

-- 2023-06-04 12:41:48
