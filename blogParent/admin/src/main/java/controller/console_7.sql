CREATE TABLE users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username    VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名（唯一）',
    password    VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
    nickname    VARCHAR(50) NOT NULL COMMENT '用户昵称',
    email       VARCHAR(100) UNIQUE COMMENT '邮箱',
    avatar      VARCHAR(255) COMMENT '头像URL',
    school      VARCHAR(100) NOT NULL COMMENT '学校',
    major       VARCHAR(100) NOT NULL COMMENT '专业',
    bio         TEXT COMMENT '个人简介',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    is_deleted  BOOLEAN DEFAULT FALSE COMMENT '软删除标记'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
CREATE TABLE posts (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '帖子ID',
    user_id     BIGINT NOT NULL COMMENT '发布用户ID',
    title       VARCHAR(255) NOT NULL COMMENT '帖子标题',
    content     TEXT NOT NULL COMMENT '帖子内容',
    views       INT DEFAULT 0 COMMENT '浏览次数',
    likes       INT DEFAULT 0 COMMENT '点赞数',
    comments    INT DEFAULT 0 COMMENT '评论数',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    is_deleted  BOOLEAN DEFAULT FALSE COMMENT '软删除标记',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='帖子表';
CREATE TABLE comments (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评论ID',
    post_id     BIGINT NOT NULL COMMENT '关联帖子ID',
    user_id     BIGINT NOT NULL COMMENT '评论用户ID',
    parent_id   BIGINT NULL COMMENT '父级评论ID（楼中楼）',
    content     TEXT NOT NULL COMMENT '评论内容',
    likes       INT DEFAULT 0 COMMENT '点赞数',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
    is_deleted  BOOLEAN DEFAULT FALSE COMMENT '软删除标记',
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (parent_id) REFERENCES comments(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';
CREATE TABLE likes (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '点赞记录ID',
    user_id     BIGINT NOT NULL COMMENT '点赞用户ID',
    target_id   BIGINT NOT NULL COMMENT '被点赞对象ID（帖子/评论）',
    type        ENUM('post', 'comment') NOT NULL COMMENT '点赞类型',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_like (user_id, target_id, type)  -- 防止重复点赞
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';
CREATE TABLE tags (
    id    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '标签ID',
    name  VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';
CREATE TABLE user_tags (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    tag_id  BIGINT NOT NULL COMMENT '标签ID',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_tag (user_id, tag_id)  -- 防止用户重复添加相同标签
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标签表';

CREATE TABLE messages (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '消息ID',
    sender_id   BIGINT NOT NULL COMMENT '发送者ID',
    receiver_id BIGINT NOT NULL COMMENT '接收者ID',
    content     TEXT NOT NULL COMMENT '消息内容',
    is_read     BOOLEAN DEFAULT FALSE COMMENT '是否已读',
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='私信表';

CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_likes_user_id ON likes(user_id);
CREATE INDEX idx_messages_sender_receiver ON messages(sender_id, receiver_id);
