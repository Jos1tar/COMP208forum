-- 插入用户数据
INSERT INTO users (username, password, nickname, email, avatar, school, major, bio)
VALUES
('user1', 'hashed_password_1', 'Alice', 'alice@example.com', NULL, 'Harvard', 'Computer Science', 'Hello, I am Alice!'),
('user2', 'hashed_password_2', 'Bob', 'bob@example.com', NULL, 'MIT', 'Mathematics', 'I love math and coding!'),
('user3', 'hashed_password_3', 'Charlie', 'charlie@example.com', NULL, 'Stanford', 'Physics', 'Physics enthusiast.');

-- 插入帖子数据
INSERT INTO posts (user_id, title, content)
VALUES
(1, 'My First Post', 'This is the content of my first post.'),
(2, 'Hello World', 'Just saying hello to everyone.'),
(3, 'Quantum Mechanics', 'Let us discuss the weirdness of quantum physics.');

-- 插入评论数据
INSERT INTO comments (post_id, user_id, content)
VALUES
(1, 2, 'Nice post!'),
(1, 3, 'Very interesting.'),
(2, 1, 'Welcome to the platform!');

-- 插入点赞数据
INSERT INTO likes (user_id, target_id, type)
VALUES
(1, 1, 'post'),
(2, 1, 'post'),
(3, 2, 'post'),
(1, 3, 'comment');

-- 插入标签数据
INSERT INTO tags (name)
VALUES
('Tech'),
('Science'),
('Programming');

-- 插入用户标签数据
INSERT INTO user_tags (user_id, tag_id)
VALUES
(1, 1),
(2, 2),
(3, 3);

-- 插入私信数据
INSERT INTO messages (sender_id, receiver_id, content)
VALUES
(1, 2, 'Hey Bob, how are you?'),
(2, 1, 'Hey Alice, I am good. How about you?'),
(3, 1, 'Alice, let’s discuss quantum physics.');
