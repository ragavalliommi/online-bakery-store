-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: online-bakery-store.czlf6to6cefj.us-east-1.rds.amazonaws.com    Database: obs
-- ------------------------------------------------------
-- Server version	8.0.28
CREATE SCHEMA IF NOT EXISTS obs;
USE obs; 

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '';

--
-- Table structure for table `Carts`
--

DROP TABLE IF EXISTS Carts;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Carts (
  EntryId int NOT NULL AUTO_INCREMENT,
  UserID int NOT NULL,
  BakeryItemID int NOT NULL,
  ItemQuantity int NOT NULL,
  ItemAmount decimal(10,3),
  Status char(10) NOT NULL,
  PRIMARY KEY (EntryId),
  KEY FKCartsUser (UserID),
  KEY FKCartsBakeryItem (BakeryItemID),
  CONSTRAINT FKCartsBakeryItem FOREIGN KEY (BakeryItemID) REFERENCES BakeryItems (BakeryItemID),
  CONSTRAINT FKCartsUser FOREIGN KEY (UserID) REFERENCES Users (UserID)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS Orders;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Orders (
  EntryID int NOT NULL AUTO_INCREMENT,
  OrderID varchar(50) NOT NULL,
  UserID int NOT NULL,
  BakeryItemID int NOT NULL,
  PaymentID int NOT NULL,
  Quantity int NOT NULL,
  Amount decimal(10,3) NOT NULL,
  OrderDate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  DeliveryMode char(5) NOT NULL,
  PRIMARY KEY (EntryId),
  KEY OrdersProduct (BakeryItemID),
  KEY OrdersPayment_idx (PaymentID),
  KEY OrdersUser (UserID),
  CONSTRAINT OrdersPayment FOREIGN KEY (PaymentID) REFERENCES Payments (PaymentID),
  CONSTRAINT OrdersProduct FOREIGN KEY (BakeryItemID) REFERENCES BakeryItems (BakeryItemID),
  CONSTRAINT OrdersUser FOREIGN KEY (UserID) REFERENCES Users (UserID)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Payments`
--

DROP TABLE IF EXISTS Payments;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Payments (
  PaymentID int NOT NULL AUTO_INCREMENT,
  PaymentMode varchar(50) NOT NULL,
  PRIMARY KEY (PaymentID)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BakeryItems`
--

DROP TABLE IF EXISTS BakeryItems;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE BakeryItems (
  BakeryItemID int NOT NULL AUTO_INCREMENT,
  ItemName varchar(100) NOT NULL,
  ItemSize varchar(100) NOT NULL,
  Price decimal(10,3) NOT NULL,
  Description varchar(100) NOT NULL,
  ImageURL varchar(1000) NOT NULL,
  PRIMARY KEY (BakeryItemID)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS Users;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Users (
  UserID int NOT NULL AUTO_INCREMENT,
  UserName varchar(100) NOT NULL UNIQUE,
  Email varchar(100) NOT NULL,
  Phone varchar(100) NOT NULL,
  Password varchar(50) NOT NULL,
  DateJoined datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  ModifiedDate datetime DEFAULT CURRENT_TIMESTAMP,
  DeliveryAddress varchar(100) NOT NULL,
  PRIMARY KEY (UserID)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
