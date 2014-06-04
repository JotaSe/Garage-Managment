-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.10 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2014-06-04 00:35:56
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for garage
DROP DATABASE IF EXISTS `garage`;
CREATE DATABASE IF NOT EXISTS `garage` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `garage`;


-- Dumping structure for table garage.customer
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `idCostumer` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '0',
  `lastName` varchar(50) DEFAULT '0',
  `idNumber` varchar(50) DEFAULT '0',
  `address` varchar(250) DEFAULT '0',
  `postalCode` varchar(10) DEFAULT '0',
  `email` varchar(50) DEFAULT '0',
  `phone` varchar(50) DEFAULT '0',
  `phone2` varchar(50) DEFAULT '0',
  PRIMARY KEY (`idCostumer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table garage.customer: ~0 rows (approximately)
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


-- Dumping structure for table garage.intervention
DROP TABLE IF EXISTS `intervention`;
CREATE TABLE IF NOT EXISTS `intervention` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicleID` int(11) DEFAULT NULL,
  `dateStart` date DEFAULT NULL,
  `dateEnd` date DEFAULT NULL,
  `notes` varchar(45) DEFAULT NULL,
  `isFinished` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vehicleId_idx` (`vehicleID`),
  CONSTRAINT `vehicleId` FOREIGN KEY (`vehicleID`) REFERENCES `vehicle` (`idVehicle`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table garage.intervention: ~0 rows (approximately)
/*!40000 ALTER TABLE `intervention` DISABLE KEYS */;
/*!40000 ALTER TABLE `intervention` ENABLE KEYS */;


-- Dumping structure for table garage.intervention_has_products
DROP TABLE IF EXISTS `intervention_has_products`;
CREATE TABLE IF NOT EXISTS `intervention_has_products` (
  `interventionId` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  PRIMARY KEY (`interventionId`,`productId`),
  UNIQUE KEY `interventionId_UNIQUE` (`interventionId`),
  UNIQUE KEY `productId_UNIQUE` (`productId`),
  CONSTRAINT `productId` FOREIGN KEY (`productId`) REFERENCES `product` (`idProduct`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `interventionId` FOREIGN KEY (`interventionId`) REFERENCES `intervention` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table garage.intervention_has_products: ~0 rows (approximately)
/*!40000 ALTER TABLE `intervention_has_products` DISABLE KEYS */;
/*!40000 ALTER TABLE `intervention_has_products` ENABLE KEYS */;


-- Dumping structure for table garage.invoice
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `notes` varchar(45) DEFAULT NULL,
  `interventionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `interventionId_idx` (`interventionId`),
  CONSTRAINT `interventionId2` FOREIGN KEY (`interventionId`) REFERENCES `intervention` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table garage.invoice: ~0 rows (approximately)
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;


-- Dumping structure for table garage.product
DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `idProduct` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `vat` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `isService` tinyint(1) DEFAULT NULL,
  `stock` double DEFAULT '0',
  PRIMARY KEY (`idProduct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table garage.product: ~0 rows (approximately)
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


-- Dumping structure for table garage.vehicle
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE IF NOT EXISTS `vehicle` (
  `idVehicle` int(10) NOT NULL AUTO_INCREMENT,
  `registrationNumber` varchar(50) DEFAULT NULL,
  `typeId` int(3) DEFAULT '0',
  `customerId` int(11) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `kmTraveled` double DEFAULT '0',
  `constructionsYear` year(4) DEFAULT NULL,
  `image` blob,
  `notes` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idVehicle`),
  UNIQUE KEY `registrationNumber` (`registrationNumber`),
  KEY `typeId` (`typeId`),
  KEY `customerId_idx` (`customerId`),
  CONSTRAINT `customerId` FOREIGN KEY (`customerId`) REFERENCES `customer` (`idCostumer`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `typeId` FOREIGN KEY (`typeId`) REFERENCES `vehicletype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table garage.vehicle: ~0 rows (approximately)
/*!40000 ALTER TABLE `vehicle` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicle` ENABLE KEYS */;


-- Dumping structure for table garage.vehicletype
DROP TABLE IF EXISTS `vehicletype`;
CREATE TABLE IF NOT EXISTS `vehicletype` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` int(3) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table garage.vehicletype: ~0 rows (approximately)
/*!40000 ALTER TABLE `vehicletype` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicletype` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
