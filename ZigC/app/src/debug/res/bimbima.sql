-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2017 at 05:36 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `bimbima`
--

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE IF NOT EXISTS `employees` (
`id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(30) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `department` varchar(50) NOT NULL,
  `position` varchar(50) NOT NULL,
  `level` int(1) NOT NULL,
  `region` varchar(30) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=72 ;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `username`, `password`, `name`, `surname`, `gender`, `department`, `position`, `level`, `region`) VALUES
(11, 'aosmani17', 'aosmani17', 'Alba', 'Osmani', 'female', 'Human Resources', 'Head of Department', 3, 'Tirane '),
(12, 'eidrizi17', 'eidrizi17', 'Erind', 'Idrizi', 'male', 'Human Resources', 'Manager', 3, 'Tirane'),
(31, 'tshkembi17', 'tshkembi17', 'Teuta', 'Shkëmbi', 'female', 'Finance Office', 'Accountant', 4, 'Tirane '),
(32, 'azela17', 'azela17', 'Artemisa', 'Zela', 'female', 'Finance Office', 'Manager', 4, 'Tirane '),
(50, 'iboci17', 'iboci17', 'Ilir', 'Boçi', 'male', 'Regional Office', 'Regional Coordinator', 1, 'Elbasan'),
(51, 'bshkurti17', 'bshkurti17', 'Bledi', 'Shkurti', 'male', 'Regional Office', 'Regional Coordinator', 1, 'Korçe'),
(52, 'bmucollari17', 'bmucollari17', 'Blerina', 'Muçollari', 'Female', 'Regional Office', 'Regional Coordinator', 1, 'Korçe'),
(70, 'mallushaj17', 'mallushaj17', 'Mario', 'Allushaj', 'male', 'Central Office', 'Supervisor', 2, 'Tirane'),
(71, 'ecanaj17', 'ecanaj17', 'Emanuela', 'Canaj', 'female', 'Central Office', 'Supervisor', 2, 'Tirane');

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

CREATE TABLE IF NOT EXISTS `expenses` (
`id` int(11) NOT NULL,
  `Beneficiary` varchar(500) NOT NULL,
  `Sum` int(11) NOT NULL,
  `Invoice` text NOT NULL,
  `Spent by` varchar(100) NOT NULL,
  `Description` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`id`, `Beneficiary`, `Sum`, `Invoice`, `Spent by`, `Description`) VALUES
(1, 'Megatek sh.a', 5000, '', 'Central Office', 'Furniture for 7 offices'),
(2, 'Adrion', 500, '', 'Regional Office', 'Supplies'),
(3, 'Agim Peza', 8000, '', 'Regional Coordinator', 'Grant approval');

-- --------------------------------------------------------

--
-- Table structure for table `file`
--

CREATE TABLE IF NOT EXISTS `file` (
`id` int(11) NOT NULL,
  `Filename` varchar(200) NOT NULL,
  `Date` date NOT NULL,
  `Name of Beneficiary Candidate` varchar(100) NOT NULL,
  `Name of Supervisor` varchar(50) NOT NULL,
  `Region` varchar(50) NOT NULL,
  `Sum of grant required` int(11) NOT NULL,
  `Location of Investment` varchar(500) NOT NULL,
  `Surface of land` int(11) NOT NULL,
  `Type of Investment` varchar(100) NOT NULL,
  `Type of crop` varchar(100) NOT NULL,
  `Additional Information` text NOT NULL,
  `Comments` text NOT NULL,
  `Status` varchar(30) NOT NULL,
  `level` int(11) NOT NULL,
  `Location` text NOT NULL,
  `Gps data` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `file`
--

INSERT INTO `file` (`id`, `Filename`, `Date`, `Name of Beneficiary Candidate`, `Name of Supervisor`, `Region`, `Sum of grant required`, `Location of Investment`, `Surface of land`, `Type of Investment`, `Type of crop`, `Additional Information`, `Comments`, `Status`, `level`, `Location`, `Gps data`) VALUES
(1, 'Agricultural Investment ', '2017-06-14', 'Agim Nezha', 'Ilir Boçi', 'Elbasan', 10000, 'Poliçan', 1000, 'Agricultural', 'Wheat, oat, rye', 'The beneficiary required this loan in order to develop a farm.', 'The possibilities of the farm are very good. \r\nAll parameters are checked.', 'Waiting for confirmation', 2, '', ''),
(2, 'Grant request2', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 2, '', ''),
(3, 'Grant request3', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 1, '', ''),
(4, 'Grant request4', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 1, '', ''),
(5, 'Grant request5', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 3, '', ''),
(6, 'Grant request6', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 4, '', ''),
(7, 'Grant request7', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 5, '', ''),
(8, 'Grant request8', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 4, '', ''),
(9, 'Grant request9', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 3, '', ''),
(10, 'Grant request10', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 1, '', ''),
(11, 'Grant request11', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 1, '', ''),
(12, 'Grant request12', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 2, '', ''),
(13, 'Grant request13', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 1, '', ''),
(14, 'Grant request14', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 5, '', ''),
(15, 'Grant request15', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 4, '', ''),
(16, 'Grant request16', '2017-05-08', 'Blerina', 'Muçollari', 'Korçe', 25000, 'Devoll', 20000, 'Planting forager trees', 'Oak tree', 'The business idea is to plan trees and later on sell it to the wooden furniture market.', 'Some clarifications still need to be made.', 'On probation', 3, '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `expenses`
--
ALTER TABLE `expenses`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `file`
--
ALTER TABLE `file`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=72;
--
-- AUTO_INCREMENT for table `expenses`
--
ALTER TABLE `expenses`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `file`
--
ALTER TABLE `file`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=17;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
