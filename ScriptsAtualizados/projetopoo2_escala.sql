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
-- Table structure for table `escala`
--

DROP TABLE IF EXISTS `escala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `escala` (
  `EscalaId` bigint unsigned NOT NULL AUTO_INCREMENT,
  `Estrutura` varchar(30) DEFAULT NULL,
  `ElementoId` int DEFAULT NULL,
  `Tipo` varchar(15) DEFAULT NULL,
  `Nome` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`EscalaId`),
  UNIQUE KEY `EscalaId` (`EscalaId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `escala`
--

LOCK TABLES `escala` WRITE;
/*!40000 ALTER TABLE `escala` DISABLE KEYS */;
INSERT INTO `escala` VALUES (1,'C-D-E-F-G-A-B',1,'Maior','C'),(2,'D-E-F#-G-A-B-C#',2,'Maior','D'),(3,'E-F#-G#-A-B-C#-D#',3,'Maior','E'),(4,'F-G-A-Bb-C-D-E',4,'Maior','F'),(5,'G-A-B-C-D-E-F#',5,'Maior','G'),(6,'A-B-C#-D-E-F#-G#',6,'Maior','A'),(7,'B-C#-D#-E-F#-G#-A#',7,'Maior','B'),(9,'C-D-Eb-F-G-Ab-Bb',8,'Menor','Cm'),(10,'D-E-F-G-A-Bb-C',9,'Menor','Dm'),(11,'E-F#-G-A-B-C-D',10,'Menor','Em'),(12,'F-G-Ab-Bb-C-Db-Eb',11,'Menor','Fm'),(13,'G-A-Bb-C-D-Eb-F',12,'Menor','Gm'),(14,'A-B-C-D-E-F-G',13,'Menor','Am'),(15,'B-C#-D-E-F#-G-A',14,'Menor','Bm');
/*!40000 ALTER TABLE `escala` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-23 22:42:54
