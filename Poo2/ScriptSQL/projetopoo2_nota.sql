-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: projetopoo2
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `nota`
--

DROP TABLE IF EXISTS `nota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nota` (
  `NotaId` bigint unsigned NOT NULL AUTO_INCREMENT,
  `Nome` varchar(30) DEFAULT NULL,
  `Acidente` varchar(30) DEFAULT NULL,
  `Oitava` int DEFAULT NULL,
  PRIMARY KEY (`NotaId`),
  UNIQUE KEY `NotaId` (`NotaId`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nota`
--

LOCK TABLES `nota` WRITE;
/*!40000 ALTER TABLE `nota` DISABLE KEYS */;
INSERT INTO `nota` VALUES (1,'C',NULL,1),(2,'C','#',1),(3,'D',NULL,1),(4,'D','#',1),(5,'E',NULL,1),(6,'F',NULL,1),(7,'F','#',1),(8,'G',NULL,1),(9,'G','#',1),(10,'A',NULL,1),(11,'A','#',1),(12,'B',NULL,1),(13,'C',NULL,2),(14,'C','#',2),(15,'D',NULL,2),(16,'D','#',2),(17,'E',NULL,2),(18,'F',NULL,2),(19,'F','#',2),(20,'G',NULL,2),(21,'G','#',2),(22,'A',NULL,2),(23,'A','#',2),(24,'B',NULL,2),(25,'C',NULL,3),(26,'C','#',3),(27,'D',NULL,3),(28,'D','#',3),(29,'E',NULL,3),(30,'F',NULL,3),(31,'F','#',3),(32,'G',NULL,3),(33,'G','#',3),(34,'A',NULL,3),(35,'A','#',3),(36,'B',NULL,3),(37,'C',NULL,4),(38,'C','#',4),(39,'D',NULL,4),(40,'D','#',4),(41,'E',NULL,4),(42,'F',NULL,4),(43,'F','#',4),(44,'G',NULL,4),(45,'G','#',4),(46,'A',NULL,4),(47,'A','#',4),(48,'B',NULL,4),(49,'C',NULL,5),(50,'C','#',5),(51,'D',NULL,5),(52,'D','#',5),(53,'E',NULL,5),(54,'F',NULL,5),(55,'F','#',5),(56,'G',NULL,5),(57,'G','#',5),(58,'A',NULL,5),(59,'A','#',5),(60,'B',NULL,5),(61,'C',NULL,6);
/*!40000 ALTER TABLE `nota` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-19 21:33:12
