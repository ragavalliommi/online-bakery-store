CREATE DATABASE  IF NOT EXISTS `obs` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `obs`;
-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: 127.0.0.1    Database: obs
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BakeryItems`
--

DROP TABLE IF EXISTS `BakeryItems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BakeryItems` (
  `BakeryItemID` int NOT NULL AUTO_INCREMENT,
  `ItemName` varchar(100) NOT NULL,
  `ItemSize` varchar(100) NOT NULL,
  `Price` double NOT NULL,
  `Description` varchar(100) NOT NULL,
  `ImageURL` varchar(1000) NOT NULL,
  PRIMARY KEY (`BakeryItemID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BakeryItems`
--

LOCK TABLES `BakeryItems` WRITE;
/*!40000 ALTER TABLE `BakeryItems` DISABLE KEYS */;
INSERT INTO `BakeryItems` VALUES (10,'Red Velvet cake','Regular',25,'The most amazing red velvet cake with cream cheese frosting','https://i.pinimg.com/564x/0e/c2/af/0ec2afb19e604d767f99723adca7c845.jpg'),(11,'Croissant','Regular',20,'Buttery, flakey croissant ','https://images.unsplash.com/photo-1555507036-ab1f4038808a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1326&q=80'),(12,'Chocolate Cake','Large',16,'A chocolate cake with two varieties of chocolate truffle (milky and dark)','https://i.pinimg.com/564x/f5/48/c8/f548c8c9677e06d722a370d28c5d98e8.jpg'),(13,'Vanilla Cake','Large',16,'A perfect cake with moist vanilla flavour','https://afairytaleflavor.com/wp-content/uploads/2021/01/DSC04487-scaled.jpg'),(14,'Apple Pie','Regular',16,'Apple pie with flaky, buttery pie crust','https://i0.wp.com/kristineskitchenblog.com/wp-content/uploads/2019/08/apple-pie-recipe-592.jpg?w=1200&ssl=1'),(15,'Pumpkin pie','Regular',16,'Pumpkin pie made with mashed, cooked pumpkin.','https://www.allrecipes.com/thmb/3J4VsfBUXyKyWbcvLr-XABFFXQg=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/13711-homemade-fresh-pumpkin-pie-mfs253-4f645b3fd23e480e8873d0cc480c4910.jpg'),(16,'Fudgy Brownie','Small',2,'A classic brownie made from melted chocolate and cocoa powder','https://vintagekitchennotes.com/wp-content/uploads/2020/05/Easy-one-bowl-brownies-featured-1.jpeg'),(17,'Cheesecake','Large',20,'Classic vanilla cheesecake baked on a graham cracker crust','https://hungryhappenings.com/wp-content/uploads/2021/04/the-best-cheesecake-recipe-creamy-smooth-delicious.jpg.webp'),(18,'Chocolate Chip Cookies','Regular',3,'Chewy vegan chocolate chip cookies','https://i.pinimg.com/564x/75/34/ec/7534ec9540c6500691e08d9931b324dc.jpg'),(19,'Chocolate cupcakes','Regular',3,'Chocolate cupcakes with moist, fluffy, full of rich chocolate flavor','https://i.pinimg.com/564x/60/83/67/6083674721bb7e4f7c70a6aa1424b666.jpg'),(20,'Pineapple Donut','Regular',2,'Moist donuts with sweet pineapple brown sugar topping','https://i.pinimg.com/564x/29/20/d1/2920d1a9772130c2557010ba5d7747a1.jpg');
/*!40000 ALTER TABLE `BakeryItems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Carts`
--

DROP TABLE IF EXISTS `Carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Carts` (
  `EntryId` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `BakeryItemID` int NOT NULL,
  `ItemQuantity` int NOT NULL,
  `ItemAmount` decimal(10,3) DEFAULT NULL,
  PRIMARY KEY (`EntryId`),
  KEY `FKCartsUser` (`UserID`),
  KEY `FKCartsBakeryItem` (`BakeryItemID`),
  CONSTRAINT `FKCartsBakeryItem` FOREIGN KEY (`BakeryItemID`) REFERENCES `BakeryItems` (`BakeryItemID`),
  CONSTRAINT `FKCartsUser` FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Carts`
--

LOCK TABLES `Carts` WRITE;
/*!40000 ALTER TABLE `Carts` DISABLE KEYS */;
INSERT INTO `Carts` VALUES (87,4,11,2,40.000);
/*!40000 ALTER TABLE `Carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Orders` (
  `OrderID` int NOT NULL,
  `UserID` int NOT NULL,
  `BakeryItemID` int NOT NULL,
  `PaymentID` int DEFAULT NULL,
  `Quantity` int NOT NULL,
  `Amount` decimal(10,3) NOT NULL,
  `OrderDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DeliveryMode` varchar(20) NOT NULL,
  `entryID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`entryID`),
  KEY `OrdersProduct` (`BakeryItemID`),
  KEY `OrdersPayment_idx` (`PaymentID`),
  KEY `OrdersUser` (`UserID`),
  CONSTRAINT `OrdersPayment` FOREIGN KEY (`PaymentID`) REFERENCES `Payments` (`PaymentID`),
  CONSTRAINT `OrdersProduct` FOREIGN KEY (`BakeryItemID`) REFERENCES `BakeryItems` (`BakeryItemID`),
  CONSTRAINT `OrdersUser` FOREIGN KEY (`UserID`) REFERENCES `Users` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Payments`
--

DROP TABLE IF EXISTS `Payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Payments` (
  `PaymentID` int NOT NULL AUTO_INCREMENT,
  `PaymentMode` varchar(50) NOT NULL,
  PRIMARY KEY (`PaymentID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Payments`
--

LOCK TABLES `Payments` WRITE;
/*!40000 ALTER TABLE `Payments` DISABLE KEYS */;
INSERT INTO `Payments` VALUES (1,'credit'),(2,'debit');
/*!40000 ALTER TABLE `Payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `UserName` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Phone` varchar(100) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `DateJoined` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ModifiedDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `DeliveryAddress` varchar(100) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (2,'biswa','biswa@gmail.com','4987839050','viswadip','2022-11-23 17:01:44','2022-11-23 17:01:44','Dallas'),(3,'Ameya','ameyapotey619@gmail.com','08056206901','ameyapotey','2022-11-24 23:38:39','2022-11-24 23:38:39','No.55, Lakshmi flats, 1st floor'),(4,'sneha','sneha@gmail.com','08056206901','sneha','2022-11-30 20:10:34','2022-11-30 20:10:34','No.55, Lakshmi flats, 1st floor');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-02 21:20:49
