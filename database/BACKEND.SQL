CREATE DATABASE IF NOT EXISTS `backend`;
USE `backend`;


-- Create the Years table
CREATE TABLE `years` (
    `year_id` INT PRIMARY KEY AUTO_INCREMENT,
    `year` INT NOT NULL
);

-- Create the Voivodeships table
CREATE TABLE `voivodeships` (
    `voivodeship_id` INT PRIMARY KEY AUTO_INCREMENT,
    `voivodeship` VARCHAR(100) NOT NULL
);

-- Create the Districts table
CREATE TABLE `districts` (
    `district_id` INT PRIMARY KEY AUTO_INCREMENT,
    `district` VARCHAR(100) NOT NULL,
    `voivodeship_id` INT,
    FOREIGN KEY (`voivodeship_id`) REFERENCES `voivodeships`(`voivodeship_id`)
);

-- Create the Population table
CREATE TABLE `populations` (
    `population_id` INT PRIMARY KEY AUTO_INCREMENT,
    `year_id` INT,
    `district_id` INT,
    `women` INT NOT NULL,
    `men` INT NOT NULL,
    FOREIGN KEY (`year_id`) REFERENCES `years`(`year_id`),   
    FOREIGN KEY (`district_id`) REFERENCES `districts`(`district_id`)
);
