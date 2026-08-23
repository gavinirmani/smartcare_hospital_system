-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smartcare_db
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `admission`
--

DROP TABLE IF EXISTS `admission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admission` (
  `admission_id` varchar(10) NOT NULL,
  `patient_id` varchar(10) NOT NULL,
  `room_id` varchar(10) NOT NULL,
  `admission_date` date NOT NULL,
  `bed_no` varchar(10) NOT NULL,
  `admission_status` enum('Admitted','Discharged') NOT NULL DEFAULT 'Admitted',
  `discharge_date` date DEFAULT NULL,
  PRIMARY KEY (`admission_id`),
  KEY `patient_id` (`patient_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `admission_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
  CONSTRAINT `admission_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admission`
--

LOCK TABLES `admission` WRITE;
/*!40000 ALTER TABLE `admission` DISABLE KEYS */;
INSERT INTO `admission` VALUES ('ADM1','P15','PR-101','2026-07-20','Bed-1','Discharged','2026-08-08'),('ADM2','P8','GW-201','2026-07-15','Bed-A','Admitted',NULL),('ADM3','P5','PR-102','2026-07-17','Bed-2','Discharged','2026-07-23'),('ADM4','P2','ICU-01','2026-07-10','Bed-1','Discharged','2026-07-13'),('ADM5','P2','GW-201','2026-07-13','Bed-C','Admitted',NULL),('ADM6','P4','ICU-02','2026-08-08','Bed-1','Admitted',NULL);
/*!40000 ALTER TABLE `admission` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_after_patient_admitted` AFTER INSERT ON `admission` FOR EACH ROW BEGIN
    UPDATE room 
    SET room_availability = 'Occupied' 
    WHERE room_id = NEW.room_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `appointment_id` varchar(10) NOT NULL,
  `patient_id` varchar(10) NOT NULL,
  `doctor_id` varchar(10) NOT NULL,
  `appointment_date` date NOT NULL,
  `appointment_time` time NOT NULL,
  `consultation_room` varchar(20) NOT NULL,
  `appointment_status` enum('Scheduled','Completed','Canceled') DEFAULT 'Scheduled',
  PRIMARY KEY (`appointment_id`),
  UNIQUE KEY `unique_doctor_schedule` (`doctor_id`,`appointment_date`,`appointment_time`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('A1','P1','DO1','2026-07-10','09:00:00','CR-101','Scheduled'),('A10','P11','DO2','2026-07-14','10:30:00','CR-102','Canceled'),('A11','P10','DO2','2026-07-14','11:30:00','CR-104','Scheduled'),('A12','P13','DO1','2026-07-15','09:00:00','CR-103','Scheduled'),('A13','P14','DO5','2026-07-15','09:30:00','CR-101','Scheduled'),('A14','P15','DO4','2026-07-16','14:30:00','CR-105','Completed'),('A15','P12','DO4','2026-07-16','11:00:00','CR-102','Scheduled'),('A16','P1','DO2','2026-08-15','10:00:00','CR-101','Scheduled'),('A2','P2','DO2','2026-07-10','09:30:00','CR-101','Canceled'),('A3','P3','DO4','2026-07-10','10:00:00','CR-102','Completed'),('A4','P4','DO3','2026-07-11','08:00:00','CR-103','Scheduled'),('A5','P5','DO3','2026-07-11','11:00:00','CR-104','Completed'),('A6','P6','DO5','2026-07-12','10:00:00','CR-102','Completed'),('A7','P7','DO5','2026-07-12','14:00:00','CR-105','Scheduled'),('A8','P8','DO1','2026-07-13','08:30:00','CR-103','Completed'),('A9','P9','DO1','2026-07-13','09:00:00','CR-101','Scheduled');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_before_appointment_insert` BEFORE INSERT ON `appointment` FOR EACH ROW BEGIN
    IF NEW.appointment_date < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Cannot book appointments for past dates!';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `bill_id` varchar(10) NOT NULL,
  `patient_id` varchar(10) NOT NULL,
  `appointment_id` varchar(10) NOT NULL,
  `bill_date` date DEFAULT NULL,
  `consultation_charges` decimal(10,2) DEFAULT '0.00',
  `room_charges` decimal(10,2) DEFAULT '0.00',
  `lab_charges` decimal(10,2) DEFAULT '0.00',
  `medicine_charges` decimal(10,2) DEFAULT '0.00',
  `total_amount` decimal(10,2) DEFAULT NULL,
  `payment_status` enum('Paid','Unpaid','Pending') NOT NULL DEFAULT 'Unpaid',
  `payment_method` enum('Cash','Card','Online') DEFAULT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `patient_id` (`patient_id`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
  CONSTRAINT `bill_ibfk_2` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`appointment_id`) ON DELETE CASCADE,
  CONSTRAINT `bill_chk_1` CHECK ((`total_amount` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES ('B1','P1','A1','2026-07-11',3500.00,0.00,0.00,2000.00,5500.00,'Unpaid',NULL),('B10','P14','A13','2026-07-16',3000.00,0.00,2500.00,1500.00,7000.00,'Paid','Online'),('B11','P2','A2','2026-07-13',0.00,30000.00,0.00,15000.00,45000.00,'Paid','Cash'),('B12','P2','A2',NULL,0.00,0.00,0.00,0.00,0.00,'Pending',NULL),('B2','P3','A3','2026-07-12',4000.00,0.00,2000.00,2000.00,8000.00,'Paid','Card'),('B3','P4','A4','2026-07-12',2000.00,0.00,0.00,500.00,2500.00,'Unpaid',NULL),('B4','P5','A5','2026-07-13',2000.00,30000.00,0.00,5000.00,37000.00,'Unpaid',NULL),('B5','P6','A6','2026-07-15',3000.00,0.00,0.00,1200.00,4200.00,'Paid','Online'),('B6','P7','A7','2026-07-13',3000.00,0.00,0.00,800.00,3800.00,'Paid','Cash'),('B7','P8','A8','2026-08-08',3500.00,0.00,0.00,400.00,3900.00,'Paid','Cash'),('B8','P10','A11','2026-07-15',2500.00,0.00,0.00,1100.00,3600.00,'Paid','Card'),('B9','P9','A9','2026-07-17',3500.00,0.00,0.00,900.00,4400.00,'Unpaid',NULL);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `department_id` varchar(10) NOT NULL,
  `department_name` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  `head_doctor` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`department_id`),
  KEY `fk_dept_head_doctor` (`head_doctor`),
  CONSTRAINT `fk_dept_head_doctor` FOREIGN KEY (`head_doctor`) REFERENCES `doctor` (`doctor_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('DE1','Cardiology','Heart Center - Level 3','DO1'),('DE2','Pediatrics','Block B - Level 2','DO2'),('DE3','Orthopedics','Block C - Level 1','DO4'),('DE4','Psychiatry','Mental Health Unit','DO3'),('DE5','Radiology','Ground Floor - Imaging Unit','DO5');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `doctor_id` varchar(10) NOT NULL,
  `doctor_name` varchar(100) NOT NULL,
  `specialization` varchar(100) NOT NULL,
  `qualification` varchar(100) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `consultation_fee` decimal(10,2) DEFAULT NULL,
  `department_id` varchar(10) NOT NULL,
  PRIMARY KEY (`doctor_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `doctor_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`) ON DELETE CASCADE,
  CONSTRAINT `doctor_chk_1` CHECK ((`consultation_fee` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES ('DO1','Dr. Nimal Perera','Cardiologist','MBBS, MD','0771234567',3500.00,'DE1'),('DO2','Dr. Sunethra Silva','Pediatrician','MBBS, DCH','0712345678',2500.00,'DE2'),('DO3','Dr. Kamal Fernando','Psychiatry','MBBS','0753456789',2000.00,'DE4'),('DO4','Dr. Anura Jayasinghe','Orthopedic Surgeon','MBBS, MS','0784567890',4000.00,'DE3'),('DO5','Dr. Chitra Gunawardena','Radiologist','MBBS, DMRD','0765678901',3000.00,'DE5');
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lab_test`
--

DROP TABLE IF EXISTS `lab_test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lab_test` (
  `lab_test_id` varchar(10) NOT NULL,
  `patient_id` varchar(10) NOT NULL,
  `doctor_id` varchar(10) NOT NULL,
  `test_name` varchar(100) NOT NULL,
  `test_date` date NOT NULL,
  `test_result` varchar(100) DEFAULT NULL,
  `technician_name` varchar(100) DEFAULT NULL,
  `lab_test_status` enum('Pending','Completed') NOT NULL DEFAULT 'Pending',
  `test_charge` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`lab_test_id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `lab_test_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
  CONSTRAINT `lab_test_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lab_test`
--

LOCK TABLES `lab_test` WRITE;
/*!40000 ALTER TABLE `lab_test` DISABLE KEYS */;
INSERT INTO `lab_test` VALUES ('L1','P1','DO1','ECG','2026-07-11','Normal Sinus Rhythm','Saman Perera','Completed',1500.00),('L2','P3','DO4','ESR & Full Blood Count','2026-07-12','Inflammatory Markers Normal','Ruwan Silva','Completed',2000.00),('L3','P14','DO5','X-Ray Chest','2026-07-16','Clear','Kamal Fernando','Completed',2500.00);
/*!40000 ALTER TABLE `lab_test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patient_id` varchar(10) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `dob` date NOT NULL,
  `gender` enum('Male','Female','Other') NOT NULL,
  `address` varchar(255) NOT NULL,
  `contact_number` varchar(15) NOT NULL,
  `blood_group` enum('A+','A-','B+','B-','AB+','AB-','O+','O-') NOT NULL,
  `emergency_contact` varchar(15) NOT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('P1','Kusal Mendis','1995-02-16','Male','Colombo','0771112223','A+','0770000001'),('P10','Matheesha Pathirana','2018-12-18','Male','Kandy','0770001112','AB-','0770000010'),('P11','Dinesh Chandimal','1989-11-18','Male','Kalutara','0771113335','O+','0770000011'),('P12','Angelo Mathews','1987-06-02','Male','Colombo','0772224446','A+','0770000012'),('P13','Dhananjaya De Silva','1991-09-06','Male','Panadura','0773335557','B+','0770000013'),('P14','Harshitha Samarawickrama','1998-06-29','Female','Galle','0774446668','O-','0770000014'),('P15','Vishmi Gunaratne','2005-08-22','Female','Ratnapura','0775557779','AB+','0770000015'),('P2','Pathum Nissanka','1998-05-18','Male','Kandy','0772223334','B+','0770000002'),('P3','Charith Asalanka','1997-06-29','Male','Galle','0773334445','O+','0770000003'),('P4','Wanindu Hasaranga','1997-07-29','Male','Matara','0774445556','AB+','0770000004'),('P5','Dasun Shanaka','1980-09-09','Male','Negombo','0775556667','A-','0770000005'),('P6','Chamari Athapaththu','1990-02-09','Female','Kurunegala','0776667778','B-','0770000006'),('P7','Inoka Ranaweera','1986-02-18','Female','Ratnapura','0777778889','O-','0770000007'),('P8','Sadaf De Silva','2000-11-12','Female','Colombo','0778889990','A+','0770000008'),('P9','Maheesh Theekshana','2000-08-01','Male','Gampaha','0779990001','B+','0770000009');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` varchar(10) NOT NULL,
  `category` enum('General Ward','Private Room','ICU') NOT NULL,
  `room_availability` enum('Available','Occupied') NOT NULL DEFAULT 'Available',
  `charge_per_day` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES ('GW-201','General Ward','Available',3000.00),('ICU-01','ICU','Occupied',10000.00),('ICU-02','ICU','Occupied',10000.00),('PR-101','Private Room','Available',5000.00),('PR-102','Private Room','Available',5000.00);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_record`
--

DROP TABLE IF EXISTS `treatment_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment_record` (
  `treatment_id` varchar(10) NOT NULL,
  `patient_id` varchar(10) NOT NULL,
  `doctor_id` varchar(10) NOT NULL,
  `diagnosis` varchar(255) NOT NULL,
  `prescription_details` varchar(255) DEFAULT NULL,
  `treatment_notes` varchar(255) DEFAULT NULL,
  `treatment_date` date NOT NULL,
  PRIMARY KEY (`treatment_id`),
  KEY `patient_id` (`patient_id`),
  KEY `doctor_id` (`doctor_id`),
  CONSTRAINT `treatment_record_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
  CONSTRAINT `treatment_record_ibfk_2` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`doctor_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_record`
--

LOCK TABLES `treatment_record` WRITE;
/*!40000 ALTER TABLE `treatment_record` DISABLE KEYS */;
INSERT INTO `treatment_record` VALUES ('T1','P3','DO4','Joint Pain - Right Wrist','NSAIDs, Blood test ordered','Routine blood work ordered to rule out inflammatory arthritis.','2026-07-12'),('T2','P8','DO1','Hypertension','Amlodipine 5mg daily','Monitor blood pressure weekly','2026-07-15'),('T3','P10','DO2','Arrhythmia Evaluation','Beta-Blocker 25mg','ECG conducted; follow-up scheduled in 2 weeks','2026-07-18'),('T4','P6','DO5','Chest Radiograph Findings','N/A - Imaging Diagnostic','X-Ray confirmed minor pulmonary congestion; report forwarded to attending physician','2026-07-15'),('T5','P5','DO3','Mild Anxiety','Anxiolytic as needed','Therapy sessions recommended','2026-07-13');
/*!40000 ALTER TABLE `treatment_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_patient_admissions`
--

DROP TABLE IF EXISTS `vw_patient_admissions`;
/*!50001 DROP VIEW IF EXISTS `vw_patient_admissions`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_patient_admissions` AS SELECT 
 1 AS `admission_id`,
 1 AS `patient_id`,
 1 AS `patient_name`,
 1 AS `room_id`,
 1 AS `category`,
 1 AS `admission_date`,
 1 AS `discharge_date`,
 1 AS `admission_status`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_unpaid_bills`
--

DROP TABLE IF EXISTS `vw_unpaid_bills`;
/*!50001 DROP VIEW IF EXISTS `vw_unpaid_bills`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_unpaid_bills` AS SELECT 
 1 AS `bill_id`,
 1 AS `patient_name`,
 1 AS `contact_number`,
 1 AS `total_amount`,
 1 AS `bill_date`,
 1 AS `payment_status`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'smartcare_db'
--
/*!50003 DROP FUNCTION IF EXISTS `fn_calculate_total_bill` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_calculate_total_bill`(
    p_consultation DECIMAL(10,2),
    p_room DECIMAL(10,2),
    p_lab DECIMAL(10,2),
    p_medicine DECIMAL(10,2)
) RETURNS decimal(10,2)
    DETERMINISTIC
BEGIN
    RETURN COALESCE(p_consultation, 0) 
         + COALESCE(p_room, 0) 
         + COALESCE(p_lab, 0) 
         + COALESCE(p_medicine, 0);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `fn_get_doctor_appointment_count` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_get_doctor_appointment_count`(p_doctor_id VARCHAR(10)) RETURNS int
    DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total FROM appointment WHERE doctor_id = p_doctor_id;
    RETURN total;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_book_appointment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_book_appointment`(
    IN p_appointment_id VARCHAR(10),
    IN p_patient_id VARCHAR(10),
    IN p_doctor_id VARCHAR(10),
    IN p_date DATE,
    IN p_time TIME,
    IN p_room VARCHAR(20)
)
BEGIN
    INSERT INTO appointment (
        appointment_id, patient_id, doctor_id, 
        appointment_date, appointment_time, consultation_room, appointment_status
    )
    VALUES (
        p_appointment_id, p_patient_id, p_doctor_id, 
        p_date, p_time, p_room, 'Scheduled'
    );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_discharge_patient` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_discharge_patient`(
    IN p_admission_id VARCHAR(10)
)
BEGIN
    DECLARE v_room_id VARCHAR(10);
    

    SELECT room_id INTO v_room_id FROM admission WHERE admission_id = p_admission_id;
    

UPDATE admission 
SET discharge_date = CURDATE(), 
    admission_status = 'Discharged' 
WHERE admission_id = p_admission_id;

UPDATE room 
SET room_availability = 'Available' 
WHERE room_id = v_room_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `vw_patient_admissions`
--

/*!50001 DROP VIEW IF EXISTS `vw_patient_admissions`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_patient_admissions` AS select `a`.`admission_id` AS `admission_id`,`p`.`patient_id` AS `patient_id`,`p`.`full_name` AS `patient_name`,`r`.`room_id` AS `room_id`,`r`.`category` AS `category`,`a`.`admission_date` AS `admission_date`,`a`.`discharge_date` AS `discharge_date`,`a`.`admission_status` AS `admission_status` from ((`admission` `a` join `patient` `p` on((`a`.`patient_id` = `p`.`patient_id`))) join `room` `r` on((`a`.`room_id` = `r`.`room_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_unpaid_bills`
--

/*!50001 DROP VIEW IF EXISTS `vw_unpaid_bills`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_unpaid_bills` AS select `b`.`bill_id` AS `bill_id`,`p`.`full_name` AS `patient_name`,`p`.`contact_number` AS `contact_number`,`b`.`total_amount` AS `total_amount`,`b`.`bill_date` AS `bill_date`,`b`.`payment_status` AS `payment_status` from (`bill` `b` join `patient` `p` on((`b`.`patient_id` = `p`.`patient_id`))) where (`b`.`payment_status` = 'Unpaid') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-08 16:28:11
