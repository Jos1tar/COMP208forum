-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 43.165.3.68    Database: LivDayDB
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `post_id` bigint NOT NULL COMMENT '关联帖子ID',
  `user_id` bigint NOT NULL COMMENT '评论用户ID',
  `parent_id` bigint DEFAULT NULL,
  `content` text NOT NULL COMMENT '评论内容',
  `likes` int DEFAULT '0' COMMENT '点赞数',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标记',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `parent_id` (`parent_id`),
  KEY `idx_comments_post_id` (`post_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `comments_ibfk_3` FOREIGN KEY (`parent_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` (`id`, `post_id`, `user_id`, `parent_id`, `content`, `likes`, `created_at`, `is_deleted`) VALUES (1,1,1,0,'Level 1 Comment 1',0,'2025-03-25 09:47:26',0),(2,1,2,0,'Level 1 Comment 2',0,'2025-03-25 09:47:26',0),(3,1,2,1,'Reply to comment 1',0,'2025-03-25 09:47:26',0),(4,1,1,2,'Reply to comment 2',0,'2025-03-25 09:47:26',0),(7,1,1,NULL,'This is a new parent comment',0,'2025-03-25 10:59:40',0),(8,1,2,1,'This is a sub-comment',0,'2025-03-25 11:19:13',0),(9,1,1,2,'This is another sub-comment',0,'2025-03-29 18:42:55',0),(10,1,3,NULL,'This is another first level comment',0,'2025-04-17 17:12:08',0),(11,1,3,NULL,'This is another first level comment',0,'2025-04-17 17:14:15',0),(12,1,3,NULL,'This is another first level comment',0,'2025-04-19 04:05:53',0),(13,7,1,NULL,'hello there',0,'2025-04-19 04:08:26',0),(14,7,1,NULL,'hello',0,'2025-04-19 04:09:55',0),(15,7,1,NULL,'This is a comment',0,'2025-04-19 04:11:15',0),(16,7,1,NULL,'123',0,'2025-04-19 08:07:15',0),(17,7,1,NULL,'hi',0,'2025-04-19 08:08:14',0),(18,7,1,NULL,'test',0,'2025-04-19 08:08:54',0),(19,7,1,NULL,'yep',0,'2025-04-19 08:09:18',0),(20,7,1,NULL,'emmm',0,'2025-04-19 08:12:56',0),(21,7,1,NULL,'123',0,'2025-04-19 08:13:45',0),(22,7,1,NULL,'2',0,'2025-04-19 08:14:07',0),(23,7,1,NULL,'hello2',0,'2025-04-19 08:14:49',0),(24,7,1,NULL,'213',0,'2025-04-19 08:15:25',0),(25,7,1,NULL,'yes',0,'2025-04-19 08:17:56',0),(26,7,1,NULL,'testing',0,'2025-04-19 08:18:29',0),(27,2,3,NULL,'222222',0,'2025-04-19 22:18:50',0),(28,9,1,NULL,'one teest comment',0,'2025-04-24 20:54:45',0),(29,9,1,NULL,'1',0,'2025-04-24 21:17:25',0),(30,9,1,NULL,'test',0,'2025-04-24 21:21:02',0),(31,11,1,NULL,'?',0,'2025-04-25 14:26:41',0);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversation`
--

DROP TABLE IF EXISTS `conversation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conversation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user1_id` bigint NOT NULL COMMENT '用户1ID',
  `user2_id` bigint NOT NULL COMMENT '用户2ID',
  `last_message` varchar(255) DEFAULT NULL COMMENT '最后一条消息',
  `last_message_time` datetime DEFAULT NULL COMMENT '最后一条消息时间',
  `unread_count_user1` int NOT NULL DEFAULT '0' COMMENT '用户1未读消息数',
  `unread_count_user2` int NOT NULL DEFAULT '0' COMMENT '用户2未读消息数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删除,1-已删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user1_user2` (`user1_id`,`user2_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会话表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversation`
--

LOCK TABLES `conversation` WRITE;
/*!40000 ALTER TABLE `conversation` DISABLE KEYS */;
INSERT INTO `conversation` (`id`, `user1_id`, `user2_id`, `last_message`, `last_message_time`, `unread_count_user1`, `unread_count_user2`, `create_time`, `update_time`, `is_deleted`) VALUES (1,1,2,'2222','2025-04-23 13:44:32',0,4,'2025-04-19 23:14:30','2025-04-23 15:58:46',0),(2,1,9,'hello','2025-04-24 20:21:12',0,8,'2025-04-23 14:56:03','2025-04-24 20:21:12',0),(3,1,8,'12123','2025-04-24 19:59:25',0,3,'2025-04-24 19:59:10','2025-04-24 19:59:25',0),(4,1,12,'234','2025-04-26 10:47:40',0,1,'2025-04-26 10:47:40','2025-04-26 10:53:13',0);
/*!40000 ALTER TABLE `conversation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `likes`
--

DROP TABLE IF EXISTS `likes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `likes` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点赞记录ID',
  `user_id` bigint NOT NULL COMMENT '点赞用户ID',
  `target_id` bigint NOT NULL COMMENT '被点赞对象ID（帖子/评论）',
  `type` enum('post','comment') NOT NULL COMMENT '点赞类型',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_like` (`user_id`,`target_id`,`type`),
  KEY `idx_likes_user_id` (`user_id`),
  CONSTRAINT `likes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `type`, `created_at`) VALUES (23,1,9,'post','2025-04-24 23:21:52'),(24,1,3,'comment','2025-04-25 02:23:08'),(25,1,11,'comment','2025-04-25 02:23:41'),(26,1,10,'comment','2025-04-25 02:25:12'),(27,1,4,'comment','2025-04-25 02:27:39');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` bigint NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `content` text NOT NULL COMMENT '消息内容',
  `is_read` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读(0-未读,1-已读)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0-未删除,1-已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_sender_receiver` (`sender_id`,`receiver_id`),
  KEY `idx_receiver` (`receiver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='私聊消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` (`id`, `sender_id`, `receiver_id`, `content`, `is_read`, `create_time`, `update_time`, `is_deleted`) VALUES (1,1,2,'778',0,'2025-04-19 23:14:30','2025-04-19 23:14:30',0),(2,1,2,'778',0,'2025-04-20 13:14:28','2025-04-20 13:14:28',0),(3,1,2,'77666',0,'2025-04-23 13:44:26','2025-04-23 13:44:26',0),(4,1,2,'2222',0,'2025-04-23 13:44:32','2025-04-23 13:44:32',0),(5,1,9,'hi',0,'2025-04-23 14:56:03','2025-04-23 14:56:03',0),(6,1,9,'are u there',0,'2025-04-23 14:57:28','2025-04-23 14:57:28',0),(7,1,9,'are u ok',0,'2025-04-23 14:59:25','2025-04-23 14:59:25',0),(8,1,9,'hi there',0,'2025-04-23 15:00:12','2025-04-23 15:00:12',0),(9,1,9,'hello',0,'2025-04-23 15:08:46','2025-04-23 15:08:46',0),(10,1,9,'hi',0,'2025-04-23 15:25:55','2025-04-23 15:25:55',0),(11,9,1,'778',1,'2025-04-23 15:32:10','2025-04-23 15:32:20',0),(12,1,9,'yes',0,'2025-04-23 15:42:18','2025-04-23 15:42:18',0),(13,1,8,'123',0,'2025-04-24 19:59:10','2025-04-24 19:59:10',0),(14,1,8,'23132',0,'2025-04-24 19:59:22','2025-04-24 19:59:22',0),(15,1,8,'12123',0,'2025-04-24 19:59:25','2025-04-24 19:59:25',0),(16,1,9,'hello',0,'2025-04-24 20:21:12','2025-04-24 20:21:12',0),(17,1,12,'234',0,'2025-04-26 10:47:40','2025-04-26 10:47:40',0);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_images`
--

DROP TABLE IF EXISTS `post_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `sort_order` int DEFAULT '0' COMMENT '图片排序',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `post_images_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_images`
--

LOCK TABLES `post_images` WRITE;
/*!40000 ALTER TABLE `post_images` DISABLE KEYS */;
INSERT INTO `post_images` (`id`, `post_id`, `image_url`, `sort_order`, `created_at`) VALUES (1,6,'https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png',0,'2025-04-02 17:18:07'),(2,7,'https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png',0,'2025-04-02 17:18:47'),(3,8,'https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png',0,'2025-04-23 11:21:39'),(4,9,'https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png',0,'2025-04-23 11:23:06'),(5,10,'https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png',0,'2025-04-25 02:15:03'),(6,12,'https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png',0,'2025-04-25 17:45:27');
/*!40000 ALTER TABLE `post_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `user_id` bigint NOT NULL COMMENT '发布用户ID',
  `username` varchar(50) NOT NULL,
  `title` varchar(255) NOT NULL COMMENT '帖子标题',
  `content` text NOT NULL COMMENT '帖子内容',
  `views` int DEFAULT '0' COMMENT '浏览次数',
  `likes` int DEFAULT '0' COMMENT '点赞数',
  `comments` int DEFAULT '0' COMMENT '评论数',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_posts_user_id` (`user_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` (`id`, `user_id`, `username`, `title`, `content`, `views`, `likes`, `comments`, `created_at`, `is_deleted`) VALUES (1,1,'','My First Post','This is the content of my first post.',4,0,0,'2023-03-22 15:15:46',0),(2,2,'','Hello World','Just saying hello to everyone.',5,0,0,'2024-03-22 15:15:46',0),(3,3,'','Quantum Mechanics','Let us discuss the weirdness of quantum physics.',2,0,0,'2025-03-22 15:15:46',0),(4,1,'user1','11','how the index of sql works',0,0,0,'2025-04-02 16:57:37',0),(5,2,'user2','22','wellll？',7,0,0,'2025-04-02 17:02:34',0),(6,2,'user2','33','wellll？',0,0,0,'2025-04-02 17:18:08',0),(7,2,'user2','44','wellll？',0,0,0,'2025-04-02 17:18:48',0),(8,2,'user2','66','wellll？',0,0,0,'2025-04-23 10:21:40',0),(9,2,'user2','77','wellll？',5,0,0,'2025-04-23 10:23:07',0),(10,2,'user2','图片上传示例2','wellll？',0,0,0,'2025-04-25 01:15:03',0),(11,1,'user01','你好','测试',0,0,0,'2025-04-25 01:18:51',0),(12,2,'user2','example2','wellll？',0,0,0,'2025-04-25 16:45:28',0);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(50) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` (`id`, `name`) VALUES (3,'Programming'),(2,'Science'),(1,'Tech');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tags`
--

DROP TABLE IF EXISTS `user_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_tag` (`user_id`,`tag_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `user_tags_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tags`
--

LOCK TABLES `user_tags` WRITE;
/*!40000 ALTER TABLE `user_tags` DISABLE KEYS */;
INSERT INTO `user_tags` (`id`, `user_id`, `tag_id`) VALUES (1,1,1),(2,2,2),(3,3,3);
/*!40000 ALTER TABLE `user_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名（唯一）',
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `school` varchar(100) DEFAULT NULL,
  `major` varchar(100) DEFAULT NULL,
  `bio` text,
  `created_at` datetime DEFAULT NULL,
  `is_deleted` tinyint DEFAULT NULL,
  `grade` varchar(20) DEFAULT NULL,
  `verification_code` varchar(10) DEFAULT NULL,
  `student_id` varchar(50) DEFAULT NULL,
  `email_verified` tinyint(1) DEFAULT '0',
  `verification_code_expire_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `email`, `avatar`, `school`, `major`, `bio`, `created_at`, `is_deleted`, `grade`, `verification_code`, `student_id`, `email_verified`, `verification_code_expire_time`, `create_time`, `update_time`) VALUES (1,'user01','hashed_password_1','Alice1','alice@example.com','https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png','Test University','Computer Science','Passion for programming and data science','2025-03-22 15:15:46',0,NULL,NULL,NULL,0,NULL,NULL,NULL),(2,'user2','hashed_password_2','Bob','bob@example.com',NULL,'MIT','Mathematics','I love math and coding!','2025-03-22 15:15:46',0,NULL,NULL,NULL,0,NULL,NULL,NULL),(3,'user3','hashed_password_3','Charlie','charlie@example.com',NULL,'Stanford','Physics','Physics enthusiast.','2025-03-22 15:15:46',0,NULL,NULL,NULL,0,NULL,NULL,NULL),(8,'jostar','$2a$10$tXFd30IFUw1HAPPDtZYU8eglvA847XB6yunzybxSjBe3kWEEsMzXK',NULL,'2980327428@qq.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'081334',NULL,1,'2025-04-12 22:05:21','2025-04-12 22:00:21','2025-04-12 22:00:21'),(9,'sghjia14','$2a$10$DaRNTLe0orPr/AlZZ9sy8uNaKeKwYC/.9Ka9tY6Nanf8jkaWklhu.',NULL,'sghjia14@liverpool.ac.uk',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'714839',NULL,0,'2025-04-19 23:21:22','2025-04-19 23:16:22','2025-04-19 23:16:22'),(12,'ahsnszy@icloud.com','$2a$10$dzapsqwPkaVRWkQSnfa7i.saBdBcFH1fR1u1NEyxBelnp9E89t1gG',NULL,'ahsnszy@icloud.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'579213',NULL,0,'2025-04-22 18:07:35','2025-04-22 18:02:35','2025-04-22 18:02:35'),(13,'jostar1','$2a$10$KB6kT/usrsztVfv57iWnqOBRZPevbMD/PU.9QZlx49WB9bLDsIRL2',NULL,'hj534029@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'642225',NULL,1,'2025-04-22 18:08:10','2025-04-22 18:03:10','2025-04-22 18:03:10'),(14,'2662376799@qq.com','$2a$10$yvYuyvuOWlyIsatZCP0oieBAkxcZHJVmxlkfTY9.1IKVNpPbgu8D6',NULL,'2662376799@qq.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'197945',NULL,0,'2025-04-23 12:06:20','2025-04-23 12:01:20','2025-04-23 12:01:20'),(15,'admin','$2a$10$i90IVWEGDVYTUae.OENI0.MKWfk6ECbIsLAwMVR4zco5jRs9YCepW',NULL,'2384513169@qq.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'883359',NULL,1,'2025-04-23 12:10:44','2025-04-23 12:05:44','2025-04-23 12:05:44'),(16,'123@qq.com','$2a$10$SNgto6yMRfzztz.quTVo7OeOvkcokfWv4L5cuFJ.lLHFO5BNJGMYe',NULL,'123@qq.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'724123',NULL,0,'2025-04-24 20:18:44','2025-04-24 20:13:44','2025-04-24 20:13:44'),(17,'123456','$2a$10$tVaiDy4b7btCWWZTQ2KXWuoOJWbl/fzKtBC94WJFXPyJUS5YK13SS',NULL,'2194970291@qq.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'050759',NULL,1,'2025-04-24 20:19:40','2025-04-24 20:14:40','2025-04-24 20:14:40'),(18,'Julie','$2a$10$zDVyANRE4Wt/nLCwHY8WmeP.gpNJr01ZmO1KFbbJy062cafu6MRhS',NULL,'18806215150@163.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'344311',NULL,1,'2025-04-25 11:15:01','2025-04-25 11:10:01','2025-04-25 11:10:01');
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

-- Dump completed on 2025-04-26 15:29:51
