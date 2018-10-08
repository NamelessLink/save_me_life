-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: take_off_pro
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cooperation`
--

DROP TABLE IF EXISTS `cooperation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cooperation` (
  `driver_id` varchar(20) NOT NULL,
  `r_id` varchar(20) NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`driver_id`,`r_id`),
  KEY `r_id` (`r_id`),
  CONSTRAINT `cooperation_ibfk_1` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`),
  CONSTRAINT `cooperation_ibfk_2` FOREIGN KEY (`r_id`) REFERENCES `restaurant` (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cooperation`
--

LOCK TABLES `cooperation` WRITE;
/*!40000 ALTER TABLE `cooperation` DISABLE KEYS */;
INSERT INTO `cooperation` VALUES ('d111','r111',0),('d111','r222',0),('d111','r333',1),('d222','r222',1),('d333','r333',1),('d444','r111',1),('d555','r222',1);
/*!40000 ALTER TABLE `cooperation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `user_id` varchar(20) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `pwd` varchar(30) DEFAULT NULL,
  `addr` varchar(100) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `city_id` varchar(20) DEFAULT NULL,
  `street_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `city_id` (`city_id`,`street_id`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`city_id`, `street_id`) REFERENCES `region` (`city_id`, `street_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('498177753130864640','nihao','qwer','tZxnvxlqR1gZHkL3ZnDOug==','西安','1234',NULL,NULL),('498240897735856128',NULL,'qwert','tZxnvxlqR1gZHkL3ZnDOug==',NULL,'1234',NULL,NULL),('u111','你好','nihao','tZxnvxlqR1gZHkL3ZnDOug==','西安','18629463451','c111','st111'),('u222','不太','butai','tZxnvxlqR1gZHkL3ZnDOug==','天津','222','c222','st222'),('u333','算了','suanle','tZxnvxlqR1gZHkL3ZnDOug==','西安','159','c333','st333');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dish` (
  `dish_id` varchar(20) NOT NULL,
  `dish_name` varchar(50) DEFAULT NULL,
  `dish_price` int(11) DEFAULT NULL,
  `d_description` varchar(100) DEFAULT NULL,
  `dish_type` varchar(20) DEFAULT NULL,
  `dish_picture` char(20) DEFAULT NULL,
  PRIMARY KEY (`dish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES ('490088387435167744','手抓饼',12,NULL,NULL,NULL),('490102445634879488','而我',50,NULL,NULL,NULL),('497414655553961984','狗肉',40,NULL,NULL,NULL),('497414704652484608','猪肉',50,NULL,NULL,NULL),('498230714490028032','肌肉',60,NULL,NULL,NULL),('498230744055676928','鸭肉',70,NULL,NULL,NULL),('498847945129328640','你好',60,NULL,NULL,NULL),('d111','水',15,'ssss','菜','s'),('d222','炸虾',50,'dddd','虾','x'),('d333','烤鸡',60,'aaaa','鸡','j');
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `driver` (
  `driver_id` varchar(20) NOT NULL,
  `driver_name` varchar(50) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `pwd` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES ('d111','张三','555',0,'zhangsan','tZxnvxlqR1gZHkL3ZnDOug=='),('d222','小二','666',0,'xiaoer','tZxnvxlqR1gZHkL3ZnDOug=='),('d333','大一','777',0,'dayi','tZxnvxlqR1gZHkL3ZnDOug=='),('d444','死死死','888',0,'sisisi','tZxnvxlqR1gZHkL3ZnDOug=='),('d555','无无无','999',0,'wuwuwu','tZxnvxlqR1gZHkL3ZnDOug==');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `menu` (
  `r_id` varchar(20) NOT NULL,
  `dish_id` varchar(20) NOT NULL,
  `sales` int(11) DEFAULT '0',
  PRIMARY KEY (`r_id`,`dish_id`),
  KEY `dish_id` (`dish_id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`r_id`) REFERENCES `restaurant` (`r_id`),
  CONSTRAINT `menu_ibfk_2` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('r111','490088387435167744',19),('r111','498230714490028032',2),('r111','498230744055676928',3),('r111','498847945129328640',0),('r222','d111',7),('r222','d333',5),('r333','d333',0);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order` (
  `order_id` varchar(20) NOT NULL,
  `state` int(11) DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  `expect_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `send_addr` varchar(100) DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `driver_id` varchar(20) DEFAULT NULL,
  `r_id` varchar(20) DEFAULT NULL,
  `dish_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `driver_id` (`driver_id`),
  KEY `order___ibfk_3` (`r_id`),
  KEY `order___ibfk_4` (`dish_id`),
  CONSTRAINT `order___ibfk_3` FOREIGN KEY (`r_id`) REFERENCES `restaurant` (`r_id`),
  CONSTRAINT `order___ibfk_4` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `customer` (`user_id`),
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('498180233013104640',2,'2018-10-06 09:37:24',NULL,NULL,'西安','u111',NULL,'r111','490088387435167744'),('498183291973537792',0,'2018-10-06 17:22:33',NULL,NULL,'西安','498177753130864640',NULL,'r111','490088387435167744'),('498231202547634176',0,'2018-10-06 20:32:55',NULL,'2018-10-06 20:39:04','西安','u111',NULL,'r111','490088387435167744'),('498232766196748288',0,'2018-10-06 20:39:08',NULL,NULL,'西安','u111',NULL,'r111','490088387435167744'),('o111',1,'2018-10-06 06:30:54',NULL,NULL,'西安','u111',NULL,'r111','d111'),('o222',1,'2018-10-06 06:31:31',NULL,NULL,'西安','u222',NULL,'r222','d222');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `region` (
  `city_id` varchar(20) NOT NULL,
  `street_id` varchar(20) NOT NULL,
  `city_name` varchar(50) DEFAULT NULL,
  `street_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`city_id`,`street_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES ('c111','st111','北京','天安门'),('c222','st222','上海','海淀'),('c333','st333','西安','小寨');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `restaurant` (
  `r_id` varchar(20) NOT NULL,
  `r_name` varchar(50) DEFAULT NULL,
  `tel` varchar(11) DEFAULT NULL,
  `r_addr` varchar(100) DEFAULT NULL,
  `owner_id` varchar(20) DEFAULT NULL,
  `city_id` varchar(20) DEFAULT NULL,
  `street_id` varchar(20) DEFAULT NULL,
  `r_description` varchar(100) DEFAULT NULL,
  `r_photo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`r_id`),
  KEY `owner_id` (`owner_id`),
  KEY `city_id` (`city_id`,`street_id`),
  CONSTRAINT `restaurant_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `seller` (`seller_id`),
  CONSTRAINT `restaurant_ibfk_2` FOREIGN KEY (`city_id`, `street_id`) REFERENCES `region` (`city_id`, `street_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES ('r111','北京饭店','147','北京天安门','s111','c111','st111','北京烤鸭',NULL),('r222','上海饭店','148','上海海淀','s222','c222','st222','不知道',NULL),('r333','西安饭店','149','西安小寨','s333','c333','st333','还是不知道',NULL);
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller` (
  `seller_id` varchar(20) NOT NULL,
  `seller_name` varchar(50) DEFAULT NULL,
  `account` varchar(20) DEFAULT NULL,
  `pwd` varchar(30) DEFAULT NULL,
  `r_addr` varchar(100) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `r_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`seller_id`),
  KEY `r_id` (`r_id`),
  CONSTRAINT `seller_ibfk_1` FOREIGN KEY (`r_id`) REFERENCES `restaurant` (`r_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES ('s111','刘六','liuliu','tZxnvxlqR1gZHkL3ZnDOug==','北京天安门','456','r111'),('s222','王五','wangwu','tZxnvxlqR1gZHkL3ZnDOug==','上海海淀','321','r222'),('s333','李四','lisi','tZxnvxlqR1gZHkL3ZnDOug==','西安小寨','123','r333');
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-08 14:12:36
