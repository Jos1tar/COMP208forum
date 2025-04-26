package ac.liverpool.forum.service;

import ac.liverpool.forum.entity.VO.PageResult;

import ac.liverpool.forum.entity.dto.PostCreateRequest;
import ac.liverpool.forum.entity.dto.PostQueryParam;
import ac.liverpool.forum.entity.Posts;
import org.springframework.stereotype.Service;

/**

 *
 * @author makejava
 * @since 2025-03-22 14:26:15
 */
@Service
public interface PostsService  {

    PageResult getPosts(PostQueryParam postQueryParam);

    Posts getPostById(Long id);

    Posts createPost(PostCreateRequest request);
}

