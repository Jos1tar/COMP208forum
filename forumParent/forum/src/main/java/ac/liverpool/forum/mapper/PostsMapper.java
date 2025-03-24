package ac.liverpool.forum.mapper;


import ac.liverpool.forum.entity.PostQueryParam;
import ac.liverpool.forum.entity.Posts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostsMapper {


    // 查询帖子
    List<Posts> list(PostQueryParam postQueryParam);


    // 根据帖子ID查询帖子详情
    @Select("SELECT p.*, u.username\n" +
            "FROM posts p\n" +
            "JOIN users u ON p.user_id = u.id\n" +
            "WHERE p.id = #{id}")
    Posts getPostById(Long id);
}
