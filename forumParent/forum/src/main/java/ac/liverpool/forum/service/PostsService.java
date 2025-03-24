package ac.liverpool.forum.service;

import ac.liverpool.forum.entity.PageResult;

import ac.liverpool.forum.entity.PostQueryParam;
import ac.liverpool.forum.entity.Posts;
import org.springframework.stereotype.Service;

/**
 * 帖子表(Posts)表服务接口
 *
 * @author makejava
 * @since 2025-03-22 14:26:15
 */
@Service
public interface PostsService  {

    PageResult getPosts(PostQueryParam postQueryParam);

    Posts getPostById(Long id);
}

