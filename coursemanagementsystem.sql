-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 13, 2022 at 04:53 AM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 8.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `coursemanagementsystem`
--
CREATE DATABASE IF NOT EXISTS `coursemanagementsystem` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `coursemanagementsystem`;

-- --------------------------------------------------------

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator` (
  `admin_id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `administrator`
--

INSERT INTO `administrator` (`admin_id`, `password`) VALUES
('Herald', 'admin'),
('2022', 'herald');

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

DROP TABLE IF EXISTS `modules`;
CREATE TABLE `modules` (
  `name` varchar(100) NOT NULL,
  `code` varchar(50) NOT NULL,
  `course` varchar(50) NOT NULL,
  `level` int(1) NOT NULL,
  `semester` int(1) NOT NULL,
  `teachers` varchar(100) DEFAULT NULL,
  `cancelled` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`name`, `code`, `course`, `level`, `semester`, `teachers`, `cancelled`) VALUES
('Accounting', '5BI2016', 'International Business', 4, 2, 'Deepson Shrestha,', NULL),
('Finance', '5BM0201', 'International Business', 4, 1, 'Aashish Acharya,', NULL),
('Object-Oriented Design and Programming', '5CS019', 'Computer Science', 5, 1, 'Bishal Khadka,', 0),
('Numerical Methods and Concurrency', '5CS021', 'Computer Science', 5, 1, 'Prabin Sapkota,', 0),
('Concepts and Technologies of AI', '5CS037', 'Computer Science', 5, 1, 'Anmol Adhikari,', 0),
('Academic Skills', 'ACD201', 'Computer Science', 4, 2, NULL, NULL),
('Critical Thinking', 'CT201', 'International Business', 6, 2, NULL, NULL),
('Project Learning', 'PRO2701', 'International Business', 5, 1, 'Anmol Adhikari,', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `name` varchar(100) NOT NULL,
  `id` int(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `course_name` varchar(100) DEFAULT NULL,
  `module_code` varchar(100) DEFAULT NULL,
  `level` varchar(100) DEFAULT NULL,
  `semester` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`name`, `id`, `password`, `course_name`, `module_code`, `level`, `semester`) VALUES
('Kushal Sarkar', 2058000, '@kushal', 'Computer Science', 'ACD201,', '4', '2'),
('Kalyan Khatry', 2058924, '@kalyan', 'Computer Science', '5CS019,5CS021,5CS037,', '5', '1'),
('Prajwal Adhikari', 2058944, '@prajwal', 'International Business', 'PRO2701,', '5', '1'),
('Sujan Neupane', 20582022, '@sujan', 'International Business', '5BM0201,', '4', '1');

-- --------------------------------------------------------

--
-- Table structure for table `students_marks`
--

DROP TABLE IF EXISTS `students_marks`;
CREATE TABLE `students_marks` (
  `id` varchar(100) NOT NULL,
  `module_code` varchar(100) NOT NULL,
  `marks` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `students_marks`
--

INSERT INTO `students_marks` (`id`, `module_code`, `marks`) VALUES
('2058924', '5CS019', '91'),
('2058924', '5CS021', '87'),
('2058924', '5CS037', '93'),
('2058944', 'PRO2701', '87');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `name` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `module_code` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`name`, `id`, `module_code`, `password`) VALUES
('Aashish Acharya', '2018', '5BM0201,', '@aashish'),
('Prabin Sapkota', '2019', '5CS021,', '@prabin'),
('Anmol Adhikari', '2020', '5CS037,PRO2701,', '@anmol'),
('Deepson Shrestha', '2021', '5BI2016,', '@deepson'),
('Bishal Khadka', '2022', '5CS019,', '@bishal');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `students_marks`
--
ALTER TABLE `students_marks`
  ADD PRIMARY KEY (`id`,`module_code`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
