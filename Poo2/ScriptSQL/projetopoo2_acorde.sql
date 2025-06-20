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
-- Table structure for table `acorde`
--

DROP TABLE IF EXISTS `acorde`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acorde` (
  `AcordeId` int unsigned NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  `TonicaId` int DEFAULT NULL,
  `EstruturaNotas` varchar(100) DEFAULT NULL,
  `ElementoId` int DEFAULT NULL,
  PRIMARY KEY (`AcordeId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acorde`
--

LOCK TABLES `acorde` WRITE;
/*!40000 ALTER TABLE `acorde` DISABLE KEYS */;
INSERT INTO `acorde` VALUES (1,'C','Maior',1,'C - E - G',1),(2,'D','Maior',2,'D - F# - A',2),(3,'E','Maior',3,'E - G# - B',3),(4,'F','Maior',4,'F - A - C',4),(5,'G','Maior',5,'G - B - D',5),(6,'A','Maior',6,'A - C# - E',6),(7,'B','Maior',7,'B - Eb - Gb',7),(8,'C','Menor',8,'C - Eb - G',8),(9,'D','Menor',9,'D - F - A',9),(10,'E','Menor',10,'E - G - B',10),(11,'F','Menor',11,'F - Ab - C',11),(12,'G','Menor',12,'G - Bb - D',12),(13,'A','Menor',13,'A - C - E',13),(14,'B','Menor',14,'B - D - Gb',14);
/*!40000 ALTER TABLE `acorde` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-19 21:33:11
