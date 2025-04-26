package ac.liverpool.forum.mapper;


import ac.liverpool.forum.entity.dto.PostQueryParam;
import ac.liverpool.forum.entity.Posts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostsMapper {


    // Search for posts
    List<Posts> list(PostQueryParam postQueryParam);


    // Query post details based on post ID
    @Select("SELECT p.*, u.username\n" +
            "FROM posts p\n" +
            "JOIN users u ON p.user_id = u.id\n" +
            "WHERE p.id = #{id}")
    Posts getPostById(Long id);

    // Insert post (return self added ID)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPost(Posts post);

    // Batch insertion of images
    int insertPostImages(@Param("postId") Long postId,
                         @Param("imageUrls") List<String> imageUrls);



   /* List<Long> getUserInteractedPostIds(@Param("userId") Long userId);
    *//**
     * 获取与用户相关的帖子互动信息
     * @param userId 用户ID
     * @return 帖子ID和互动用户列表的映射
     *//*
    List<Map<String, Object>> getPostInteractions(Long userId);

    *//**
     * 根据帖子ID列表批量查询帖子
     * @param postIds 帖子ID列表
     * @return 帖子列表
     *//*
    List<Post> getPostsByIds(@Param("postIds") List<Long> postIds);*/
}
