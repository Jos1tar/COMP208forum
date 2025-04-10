-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: forum
-- ------------------------------------------------------
-- Server version	8.0.39

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` (`id`, `post_id`, `user_id`, `parent_id`, `content`, `likes`, `created_at`, `is_deleted`) VALUES (1,1,1,0,'一级评论1',0,'2025-03-25 09:47:26',0),(2,1,2,0,'一级评论2',0,'2025-03-25 09:47:26',0),(3,1,2,1,'回复评论1',0,'2025-03-25 09:47:26',0),(4,1,1,2,'回复评论2',0,'2025-03-25 09:47:26',0),(7,1,1,NULL,'这是一个新的父评论',0,'2025-03-25 10:59:40',0),(8,1,2,1,'这是一个子评论',0,'2025-03-25 11:19:13',0),(9,1,1,2,'这是另外一个子评论',0,'2025-03-29 18:42:55',0);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `likes`
--

LOCK TABLES `likes` WRITE;
/*!40000 ALTER TABLE `likes` DISABLE KEYS */;
INSERT INTO `likes` (`id`, `user_id`, `target_id`, `type`, `created_at`) VALUES (1,1,1,'post','2025-03-22 15:15:47'),(2,2,1,'post','2025-03-22 15:15:47'),(3,3,2,'post','2025-03-22 15:15:47'),(5,1,4,'comment','2025-04-01 18:26:46'),(6,2,4,'comment','2025-04-01 18:26:54');
/*!40000 ALTER TABLE `likes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` bigint NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `content` text NOT NULL COMMENT '消息内容',
  `is_read` tinyint(1) DEFAULT '0' COMMENT '是否已读',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `receiver_id` (`receiver_id`),
  KEY `idx_messages_sender_receiver` (`sender_id`,`receiver_id`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='私信表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` (`id`, `sender_id`, `receiver_id`, `content`, `is_read`, `created_at`) VALUES (1,1,2,'Hey Bob, how are you?',0,'2025-03-22 15:15:47'),(2,2,1,'Hey Alice, I am good. How about you?',0,'2025-03-22 15:15:47'),(3,3,1,'Alice, let’s discuss quantum physics.',0,'2025-03-22 15:15:47');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_images`
--

LOCK TABLES `post_images` WRITE;
/*!40000 ALTER TABLE `post_images` DISABLE KEYS */;
INSERT INTO `post_images` (`id`, `post_id`, `image_url`, `sort_order`, `created_at`) VALUES (1,6,'https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png',0,'2025-04-02 17:18:07'),(2,7,'https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png',0,'2025-04-02 17:18:47');
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` (`id`, `user_id`, `username`, `title`, `content`, `views`, `likes`, `comments`, `created_at`, `is_deleted`) VALUES (1,1,'','My First Post','This is the content of my first post.',4,0,0,'2023-03-22 15:15:46',0),(2,2,'','Hello World','Just saying hello to everyone.',5,0,0,'2024-03-22 15:15:46',0),(3,3,'','Quantum Mechanics','Let us discuss the weirdness of quantum physics.',2,0,0,'2025-03-22 15:15:46',0),(4,1,'user1','关于数据库的讨论','请问大家 MySQL 的索引是如何工作的？',0,0,0,'2025-04-02 16:57:37',0),(5,2,'user2','图片上传示例','wellll？',0,0,0,'2025-04-02 17:02:34',0),(6,2,'user2','图片上传示例2','wellll？',0,0,0,'2025-04-02 17:18:08',0),(7,2,'user2','图片上传示例2','wellll？',0,0,0,'2025-04-02 17:18:48',0);
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
  `password` varchar(255) NOT NULL COMMENT '密码（加密存储）',
  `nickname` varchar(50) NOT NULL COMMENT '用户昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `school` varchar(100) NOT NULL COMMENT '学校',
  `major` varchar(100) NOT NULL COMMENT '专业',
  `bio` text COMMENT '个人简介',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '软删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `username`, `password`, `nickname`, `email`, `avatar`, `school`, `major`, `bio`, `created_at`, `is_deleted`) VALUES (1,'user01','hashed_password_1','Alice','alice@example.com','https://comp208.oss-eu-west-1.aliyuncs.com/2025/04/66b8020c-8797-48d2-b412-4f0d938a1867.png','Test University','Computer Science','热爱编程与数据科学','2025-03-22 15:15:46',0),(2,'user2','hashed_password_2','Bob','bob@example.com',NULL,'MIT','Mathematics','I love math and coding!','2025-03-22 15:15:46',0),(3,'user3','hashed_password_3','Charlie','charlie@example.com',NULL,'Stanford','Physics','Physics enthusiast.','2025-03-22 15:15:46',0);
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

-- Dump completed on 2025-04-10 17:09:06
