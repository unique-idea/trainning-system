CREATE DATABASE  IF NOT EXISTS `fa_training_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fa_training_system`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: fa_training_system
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `assessments`
--

DROP TABLE IF EXISTS `assessments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assessments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `assignment` float DEFAULT NULL,
  `final` float DEFAULT NULL,
  `final_practice` float DEFAULT NULL,
  `final_theory` float DEFAULT NULL,
  `gpa` float DEFAULT NULL,
  `quiz` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessments`
--

LOCK TABLES `assessments` WRITE;
/*!40000 ALTER TABLE `assessments` DISABLE KEYS */;
INSERT INTO `assessments` VALUES (1,15,15,70,40,60,70),(2,20,20,60,30,70,80);
/*!40000 ALTER TABLE `assessments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendees`
--

DROP TABLE IF EXISTS `attendees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_75u38dvm5ht4p4tudfqbb1prh` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendees`
--

LOCK TABLES `attendees` WRITE;
/*!40000 ALTER TABLE `attendees` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_details`
--

DROP TABLE IF EXISTS `class_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accepted` int DEFAULT NULL,
  `actual` int DEFAULT NULL,
  `finish_at` time DEFAULT NULL,
  `others` varchar(200) DEFAULT NULL,
  `planned` int DEFAULT NULL,
  `start_at` time DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `attendee_id` bigint DEFAULT NULL,
  `class_id` bigint NOT NULL,
  `location_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrox1p5mwmlawehs4sqae6f2cu` (`attendee_id`),
  KEY `FKjuc5vv7suu2rg9fe9j14nl5vp` (`class_id`),
  KEY `FKqa7bkgps47qjh7cxwsa9gqwks` (`location_id`),
  CONSTRAINT `FKjuc5vv7suu2rg9fe9j14nl5vp` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`),
  CONSTRAINT `FKqa7bkgps47qjh7cxwsa9gqwks` FOREIGN KEY (`location_id`) REFERENCES `locations` (`id`),
  CONSTRAINT `FKrox1p5mwmlawehs4sqae6f2cu` FOREIGN KEY (`attendee_id`) REFERENCES `attendees` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_details`
--

LOCK TABLES `class_details` WRITE;
/*!40000 ALTER TABLE `class_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_schedules`
--

DROP TABLE IF EXISTS `class_schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_schedules` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `study_date` date DEFAULT NULL,
  `class_detail_id` bigint DEFAULT NULL,
  `trainer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3hr653l17gogn08c6or9junst` (`class_detail_id`),
  KEY `FKfgq5lye3c64by4udqajlr279g` (`trainer_id`),
  CONSTRAINT `FK3hr653l17gogn08c6or9junst` FOREIGN KEY (`class_detail_id`) REFERENCES `class_details` (`id`),
  CONSTRAINT `FKfgq5lye3c64by4udqajlr279g` FOREIGN KEY (`trainer_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_schedules`
--

LOCK TABLES `class_schedules` WRITE;
/*!40000 ALTER TABLE `class_schedules` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `code` varchar(50) NOT NULL,
  `duration` int DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `last_modified_by` bigint DEFAULT NULL,
  `program_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ivcaxrbwnp0dosg2gj4i3sxpq` (`code`),
  KEY `FKbiegjf3djr7vcre1jxun3lbhh` (`created_by`),
  KEY `FKd9jxhyf732e1o7ayctf7yuugp` (`last_modified_by`),
  KEY `FKthmk4awc7ft3lg8ud2behicfs` (`program_id`),
  CONSTRAINT `FKbiegjf3djr7vcre1jxun3lbhh` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKd9jxhyf732e1o7ayctf7yuugp` FOREIGN KEY (`last_modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKthmk4awc7ft3lg8ud2behicfs` FOREIGN KEY (`program_id`) REFERENCES `programs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveries`
--

DROP TABLE IF EXISTS `deliveries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliveries` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_23btti2t7198im5s1enq84xwh` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveries`
--

LOCK TABLES `deliveries` WRITE;
/*!40000 ALTER TABLE `deliveries` DISABLE KEYS */;
/*!40000 ALTER TABLE `deliveries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `format_types`
--

DROP TABLE IF EXISTS `format_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `format_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ry7mnncrfp0a7d2q590u6hx0t` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `format_types`
--

LOCK TABLES `format_types` WRITE;
/*!40000 ALTER TABLE `format_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `format_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lessons`
--

DROP TABLE IF EXISTS `lessons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lessons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `duration` int DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `delivery_id` bigint DEFAULT NULL,
  `format_type_id` bigint DEFAULT NULL,
  `output_standard_id` bigint DEFAULT NULL,
  `unit_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK18qao7xmeh7a8e1i0s3q3ik0e` (`delivery_id`),
  KEY `FKemvrikd529cwhlk515kim2gve` (`format_type_id`),
  KEY `FK8r17xqds2ig0mlk9jm0e53wcm` (`output_standard_id`),
  KEY `FKfsvqlgk1jdfcd1h8biuwy55rn` (`unit_id`),
  CONSTRAINT `FK18qao7xmeh7a8e1i0s3q3ik0e` FOREIGN KEY (`delivery_id`) REFERENCES `deliveries` (`id`),
  CONSTRAINT `FK8r17xqds2ig0mlk9jm0e53wcm` FOREIGN KEY (`output_standard_id`) REFERENCES `output_standards` (`id`),
  CONSTRAINT `FKemvrikd529cwhlk515kim2gve` FOREIGN KEY (`format_type_id`) REFERENCES `format_types` (`id`),
  CONSTRAINT `FKfsvqlgk1jdfcd1h8biuwy55rn` FOREIGN KEY (`unit_id`) REFERENCES `units` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lessons`
--

LOCK TABLES `lessons` WRITE;
/*!40000 ALTER TABLE `lessons` DISABLE KEYS */;
/*!40000 ALTER TABLE `lessons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `levels`
--

DROP TABLE IF EXISTS `levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `levels` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e1ejnmsswocdocld1t1iwsvr5` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `levels`
--

LOCK TABLES `levels` WRITE;
/*!40000 ALTER TABLE `levels` DISABLE KEYS */;
INSERT INTO `levels` VALUES (3,'Advanced'),(1,'Basic'),(2,'Intermediate');
/*!40000 ALTER TABLE `levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `locations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(45) NOT NULL,
  `fsu` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materials`
--

DROP TABLE IF EXISTS `materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materials` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `lesson_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj3unvdjwlsccmqod6jl4w0vcp` (`created_by`),
  KEY `FK24ruqpibfurlhb345nmqh08n` (`lesson_id`),
  CONSTRAINT `FK24ruqpibfurlhb345nmqh08n` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`id`),
  CONSTRAINT `FKj3unvdjwlsccmqod6jl4w0vcp` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materials`
--

LOCK TABLES `materials` WRITE;
/*!40000 ALTER TABLE `materials` DISABLE KEYS */;
/*!40000 ALTER TABLE `materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `output_standards`
--

DROP TABLE IF EXISTS `output_standards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `output_standards` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3y4rci6j3m36u8wvs5fc35txy` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `output_standards`
--

LOCK TABLES `output_standards` WRITE;
/*!40000 ALTER TABLE `output_standards` DISABLE KEYS */;
/*!40000 ALTER TABLE `output_standards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program_syllabus`
--

DROP TABLE IF EXISTS `program_syllabus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_syllabus` (
  `program_id` bigint NOT NULL,
  `syllabus_id` bigint NOT NULL,
  `syllabus_index` int NOT NULL,
  PRIMARY KEY (`program_id`,`syllabus_index`),
  KEY `FK8ks7nksqr16bpi4niyc86j8ap` (`syllabus_id`),
  CONSTRAINT `FK8ks7nksqr16bpi4niyc86j8ap` FOREIGN KEY (`syllabus_id`) REFERENCES `syllabuses` (`id`),
  CONSTRAINT `FKdfxu0lcxgkxe849mafs4i6v9m` FOREIGN KEY (`program_id`) REFERENCES `programs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_syllabus`
--

LOCK TABLES `program_syllabus` WRITE;
/*!40000 ALTER TABLE `program_syllabus` DISABLE KEYS */;
/*!40000 ALTER TABLE `program_syllabus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programs`
--

DROP TABLE IF EXISTS `programs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `activated` bit(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `last_modified_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4uh20rvftatfys82a9nkvpl0o` (`name`),
  KEY `FKlrp6u3c081ppocuppb676c7gy` (`created_by`),
  KEY `FK4cyb4heqhy4xgoy2pfuuwjqdq` (`last_modified_by`),
  CONSTRAINT `FK4cyb4heqhy4xgoy2pfuuwjqdq` FOREIGN KEY (`last_modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKlrp6u3c081ppocuppb676c7gy` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programs`
--

LOCK TABLES `programs` WRITE;
/*!40000 ALTER TABLE `programs` DISABLE KEYS */;
/*!40000 ALTER TABLE `programs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `permissions` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Super Admin','Syllabus_FullAccess,Program_FullAccess,Class_FullAccess,Material_FullAccess,User_FullAccess'),(2,'Class Admin','Syllabus_FullAccess,Program_FullAccess,Class_FullAccess,Material_FullAccess,User_AccessDenied'),(3,'Trainer','Syllabus_Create,Program_View,Class_Modify,Material_FullAccess,User_AccessDenied'),(4,'Trainee','Syllabus_View,Program_View,Class_View,Material_View,User_AccessDenied');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `index` int DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `syllabus_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh0x2ptmxtqkfbjhbkoevwkts7` (`syllabus_id`),
  CONSTRAINT `FKh0x2ptmxtqkfbjhbkoevwkts7` FOREIGN KEY (`syllabus_id`) REFERENCES `syllabuses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syllabuses`
--

DROP TABLE IF EXISTS `syllabuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `syllabuses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `attendee_number` int DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `course_objective` text,
  `name` varchar(100) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `technical_requirement` text,
  `training_principle` text,
  `created_by` bigint DEFAULT NULL,
  `last_modified_by` bigint DEFAULT NULL,
  `level_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e7s6kp3i6cm5rbr4teubcgey4` (`code`),
  KEY `FK1ysb28n6acuj6ka2qbk7jfxl9` (`created_by`),
  KEY `FKavtmf636td29vit0r4hufwr0a` (`last_modified_by`),
  KEY `FKtlybknyldvpkfyk329rgo8hlw` (`level_id`),
  CONSTRAINT `FK1ysb28n6acuj6ka2qbk7jfxl9` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKavtmf636td29vit0r4hufwr0a` FOREIGN KEY (`last_modified_by`) REFERENCES `users` (`id`),
  CONSTRAINT `FKtlybknyldvpkfyk329rgo8hlw` FOREIGN KEY (`level_id`) REFERENCES `levels` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syllabuses`
--

LOCK TABLES `syllabuses` WRITE;
/*!40000 ALTER TABLE `syllabuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `syllabuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `units` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `index` int DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `session_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKggkr7eghfph2og9yk21721di8` (`session_id`),
  CONSTRAINT `FKggkr7eghfph2og9yk21721di8` FOREIGN KEY (`session_id`) REFERENCES `sessions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_class_detail`
--

DROP TABLE IF EXISTS `user_class_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_class_detail` (
  `class_detail_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FKjhfajenjj6qnbiugckn0j7kak` (`user_id`),
  KEY `FKq6rrotmrcevydf36copjv6ppb` (`class_detail_id`),
  CONSTRAINT `FKjhfajenjj6qnbiugckn0j7kak` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKq6rrotmrcevydf36copjv6ppb` FOREIGN KEY (`class_detail_id`) REFERENCES `class_details` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_class_detail`
--

LOCK TABLES `user_class_detail` WRITE;
/*!40000 ALTER TABLE `user_class_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_class_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activated` bit(1) NOT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `gender` bit(1) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `level_id` bigint DEFAULT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKhjemv8nqu3o0rutwrhlgca5in` (`level_id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKhjemv8nqu3o0rutwrhlgca5in` FOREIGN KEY (`level_id`) REFERENCES `levels` (`id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,_binary '','http://image.com','1998-05-14','tuan@gmail.com','Tran Manh Tuan',_binary '','$2a$10$JZHRcfts3vqwXl3b/.ctc.T1OffwgqZteBjSlkAeizcIBIzHRcmDq',2,4),(3,_binary '','http://image.com','1987-12-25','admin@gmail.com','Pham Nhat Minh',_binary '','$2a$10$0a3XB92RIgLhwn1Y.ejTV.6Vh.e52YMGg6d8oe1e8j3xBuRzvNDUO',NULL,1),(4,_binary '','http://image.com','1989-06-17','khanh@gmail.com','Kieu Trong Khanh',_binary '','$2a$10$OewoNO4KejOB07FnQ2atbeOmlLcHEkny1Nn96A5uShAFkeJ3BhH82',NULL,3),(5,_binary '','http://image.com','1996-04-11','dieu@gmail.com','Nguyen Thi My Dieu',_binary '\0','$2a$10$eYZwt/0P.FOIVKjkcAKSkecQhEyf4w2/WfQ/dr.jVrn3bGKsSuXnW',NULL,2);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-08 12:21:55
CREATE DATABASE  IF NOT EXISTS `fa_training_system` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fa_training_system`;
-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: localhost    Database: fa_training_system
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `attendee`
--

DROP TABLE IF EXISTS `attendee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_detail_id` bigint DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Attendee_CD_idx` (`class_detail_id`),
  CONSTRAINT `FK_Attendee_CD` FOREIGN KEY (`class_detail_id`) REFERENCES `class_detail` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendee`
--

LOCK TABLES `attendee` WRITE;
/*!40000 ALTER TABLE `attendee` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `program_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `class_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `class_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `duration` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `program_id_idx` (`program_id`),
  KEY `FK_Class_User_idx` (`created_by`),
  CONSTRAINT `FK_Class_User` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_PC_id` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_detail`
--

DROP TABLE IF EXISTS `class_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_id` bigint DEFAULT NULL,
  `program_id` bigint DEFAULT NULL,
  `location_id` bigint DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `training_program` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `attendee_type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `fsu` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `class_location` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `others` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `planned` int DEFAULT NULL,
  `accepted` int DEFAULT NULL,
  `actual` int DEFAULT NULL,
  `start_at` time DEFAULT NULL,
  `finish_at` time DEFAULT NULL,
  `class_detailcol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `class_id_idx` (`class_id`),
  KEY `FK_CD_Program_idx` (`program_id`),
  KEY `FK_CD_Location_idx` (`location_id`),
  CONSTRAINT `FK_CD_class_id` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`),
  CONSTRAINT `FK_CD_Location` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_CD_Program` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_detail`
--

LOCK TABLES `class_detail` WRITE;
/*!40000 ALTER TABLE `class_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_schedule`
--

DROP TABLE IF EXISTS `class_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_detail_id` bigint DEFAULT NULL,
  `trainer_id` bigint DEFAULT NULL,
  `study_day` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CS_User_idx` (`trainer_id`),
  KEY `FK_CS_CD_idx` (`class_detail_id`),
  CONSTRAINT `FK_CS_CD` FOREIGN KEY (`class_detail_id`) REFERENCES `class_detail` (`id`),
  CONSTRAINT `FK_CS_Trainer_id` FOREIGN KEY (`trainer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_schedule`
--

LOCK TABLES `class_schedule` WRITE;
/*!40000 ALTER TABLE `class_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_syllabus`
--

DROP TABLE IF EXISTS `class_syllabus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_syllabus` (
  `syllabus_id` bigint DEFAULT NULL,
  `class_id` bigint DEFAULT NULL,
  KEY `class_id_idx` (`class_id`),
  KEY `FK_CS_Syllabus_idx` (`syllabus_id`),
  CONSTRAINT `FK_CS_class_id` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`),
  CONSTRAINT `FK_CS_Syllabus` FOREIGN KEY (`syllabus_id`) REFERENCES `syllabus` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_syllabus`
--

LOCK TABLES `class_syllabus` WRITE;
/*!40000 ALTER TABLE `class_syllabus` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_syllabus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES (1,'Assignment/Lab'),(2,'Concept/Lecture'),(3,'Guide/Review'),(4,'Test/Quiz'),(5,'Exam'),(6,'Seminar/Workshop');
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `class_id` bigint NOT NULL,
  `program_id` bigint NOT NULL,
  `description` text,
  `uploadFile` blob,
  `status` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT 'Pending',
  `feedbackcol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_FB_User_idx` (`user_id`),
  KEY `FK_FB_Class_idx` (`class_id`),
  KEY `FK_FB_Program_idx` (`program_id`),
  CONSTRAINT `FK_FB_Class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`),
  CONSTRAINT `FK_FB_Program` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`),
  CONSTRAINT `FK_FB_User` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `format_type`
--

DROP TABLE IF EXISTS `format_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `format_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `format_type`
--

LOCK TABLES `format_type` WRITE;
/*!40000 ALTER TABLE `format_type` DISABLE KEYS */;
INSERT INTO `format_type` VALUES (1,'Online'),(2,'Offline');
/*!40000 ALTER TABLE `format_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lessons`
--

DROP TABLE IF EXISTS `lessons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lessons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `unit_id` bigint DEFAULT NULL,
  `output_standard_id` bigint DEFAULT NULL,
  `delivery_id` bigint DEFAULT NULL,
  `format_type_id` bigint DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `output_standard_idx` (`output_standard_id`),
  KEY `delivery_type_idx` (`delivery_id`),
  KEY `FK_UD_Unit_idx` (`unit_id`),
  KEY `FK_Lessons_FT_idx` (`format_type_id`),
  CONSTRAINT `FK_Lessons_FT` FOREIGN KEY (`format_type_id`) REFERENCES `format_type` (`id`),
  CONSTRAINT `FK_UD_delivery_type` FOREIGN KEY (`delivery_id`) REFERENCES `delivery` (`id`),
  CONSTRAINT `FK_UD_output_standard` FOREIGN KEY (`output_standard_id`) REFERENCES `output_standard` (`id`),
  CONSTRAINT `FK_UD_Unit` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lessons`
--

LOCK TABLES `lessons` WRITE;
/*!40000 ALTER TABLE `lessons` DISABLE KEYS */;
INSERT INTO `lessons` VALUES (1,1,1,3,1,'Java Introduction',30,'1'),(2,1,1,3,2,'What is Java?',45,'1'),(3,2,1,2,2,'Java learning 1',45,'1'),(4,2,1,2,2,'Java learning 2',45,'1');
/*!40000 ALTER TABLE `lessons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `level`
--

DROP TABLE IF EXISTS `level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `level` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `level`
--

LOCK TABLES `level` WRITE;
/*!40000 ALTER TABLE `level` DISABLE KEYS */;
INSERT INTO `level` VALUES (1,'OJT'),(2,'ABC');
/*!40000 ALTER TABLE `level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(45) DEFAULT NULL,
  `fsu` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lesson_id` bigint DEFAULT NULL,
  `uploadFile` blob,
  `created_by` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `upload_file` varbinary(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Material_Lessons_idx` (`lesson_id`),
  CONSTRAINT `FK_Material_Lessons` FOREIGN KEY (`lesson_id`) REFERENCES `lessons` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (1,1,NULL,'truongnln@gmail.com','2023-03-07 15:48:23',NULL),(2,1,NULL,'truongnln@gmail.com','2023-03-07 15:48:42',NULL);
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `output_standard`
--

DROP TABLE IF EXISTS `output_standard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `output_standard` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '	',
  `name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `output_standard`
--

LOCK TABLES `output_standard` WRITE;
/*!40000 ALTER TABLE `output_standard` DISABLE KEYS */;
INSERT INTO `output_standard` VALUES (1,'Basic'),(2,'Advance'),(3,'Super'),(4,'OJT'),(5,'ABCD');
/*!40000 ALTER TABLE `output_standard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program`
--

DROP TABLE IF EXISTS `program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Program_Modified_by_idx` (`modified_by`),
  KEY `FK_Program_Created_by_idx` (`created_by`),
  CONSTRAINT `FK_Program_Created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_Program_Modified_by` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program`
--

LOCK TABLES `program` WRITE;
/*!40000 ALTER TABLE `program` DISABLE KEYS */;
/*!40000 ALTER TABLE `program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program_syllabus`
--

DROP TABLE IF EXISTS `program_syllabus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_syllabus` (
  `syllabus_id` bigint DEFAULT NULL,
  `program_id` bigint DEFAULT NULL,
  KEY `program_id_idx` (`program_id`),
  KEY `FK_PS_Syllabus_idx` (`syllabus_id`),
  CONSTRAINT `FK_PS_program_id` FOREIGN KEY (`program_id`) REFERENCES `program` (`id`),
  CONSTRAINT `FK_PS_Syllabus` FOREIGN KEY (`syllabus_id`) REFERENCES `syllabus` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_syllabus`
--

LOCK TABLES `program_syllabus` WRITE;
/*!40000 ALTER TABLE `program_syllabus` DISABLE KEYS */;
/*!40000 ALTER TABLE `program_syllabus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `permissions` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin','FullAccess'),(2,'User','Syllabus_View,Program_View,Class_View,User_View');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `syllabus_id` bigint DEFAULT NULL,
  `index` int DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Session_Syllabus_idx` (`syllabus_id`),
  CONSTRAINT `FK_Session_Syllabus` FOREIGN KEY (`syllabus_id`) REFERENCES `syllabus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (1,1,1,'Saved',NULL),(2,1,2,'Saved',NULL),(3,2,1,'Draft',NULL),(4,2,2,'Draft',NULL);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syllabus`
--

DROP TABLE IF EXISTS `syllabus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `syllabus` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `level_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `modified_by` bigint DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `attendee_number` int DEFAULT NULL,
  `technical_requirement` text,
  `status` varchar(20) DEFAULT NULL,
  `course_objective` text,
  `training_principle` text,
  PRIMARY KEY (`id`),
  KEY `FK_Syllabus_Level_idx` (`level_id`),
  KEY `FK_Syllabus_User_idx` (`modified_by`),
  KEY `FK_SU_created_by_idx` (`created_by`),
  CONSTRAINT `FK_SU_created_by` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_SU_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_Syllabus_Level` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syllabus`
--

LOCK TABLES `syllabus` WRITE;
/*!40000 ALTER TABLE `syllabus` DISABLE KEYS */;
INSERT INTO `syllabus` VALUES (1,1,2,2,'JAVA OJT','JOJT','2023-03-07 12:25:31','2023-03-07 12:25:39',NULL,20,'Trainee\'s PCs need to have following software installed & run without any issues: Microsoft SQL Server 2005 Express (in which the trainees can create & manipulate on their own database) Netbeans IDE Microsoft Office 2007 ','1','This topic is to introduce about JAVA programming language knowledge; adapt trainees with skills, lessons and practices which is specifically used in the: Fsoft project','Training_ABCDEF,Re-test_Only allow each student to play game,Marking_abcdef'),(2,1,2,2,'.NET','OJTNET','2023-03-07 13:18:47','2023-03-07 13:18:47',NULL,40,'Trainee\'s PCs need to have following software installed & run without any issues: Microsoft SQL Server 2005 Express (in which the trainees can create & manipulate on their own database) Netbeans IDE Microsoft Office 2007 ','1','This topic is to introduce about JAVA programming language knowledge; adapt trainees with skills, lessons and practices which is specifically used in the: Fsoft project','Training_ABCDEF,Re-test_Only allow each student to play game,Marking_abcdef');
/*!40000 ALTER TABLE `syllabus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unit` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `session_id` bigint DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `index` int DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Unit_Session_idx` (`session_id`),
  CONSTRAINT `FK_Unit_Session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES (1,1,NULL,'Java Introduction',1,'1'),(2,1,NULL,'Java Basic 1',2,'1'),(3,2,NULL,'Java Basic 2',3,'1'),(4,2,NULL,'Java OOP',4,'1');
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `level_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL,
  `fullName` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `gender` tinyint DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `avatar` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_role_idx` (`role_id`),
  KEY `FK_User_Level_idx` (`level_id`),
  CONSTRAINT `FK_User_Level` FOREIGN KEY (`level_id`) REFERENCES `level` (`id`),
  CONSTRAINT `FK_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,2,'Nguyen Le Nhat Truong','truongnln1@gmail.com','12345','2001-01-11',0,1,NULL),(2,2,1,'Nguyen Le Nhat Truong','truongnln@gmail.com','12345','2001-01-11',0,1,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_class_detail`
--

DROP TABLE IF EXISTS `user_class_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_class_detail` (
  `user_id` bigint DEFAULT NULL,
  `class_detail_id` bigint DEFAULT NULL,
  KEY `FK_User_CD_idx` (`user_id`),
  KEY `FK_CD_User_idx` (`class_detail_id`),
  CONSTRAINT `FK_CD_User` FOREIGN KEY (`class_detail_id`) REFERENCES `class_detail` (`id`),
  CONSTRAINT `FK_User_CD` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_class_detail`
--

LOCK TABLES `user_class_detail` WRITE;
/*!40000 ALTER TABLE `user_class_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_class_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-08  8:44:42
