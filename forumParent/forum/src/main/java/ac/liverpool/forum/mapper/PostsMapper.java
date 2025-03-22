package ac.liverpool.forum.mapper;


import ac.liverpool.forum.entity.PostQueryParam;
import ac.liverpool.forum.entity.Posts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostsMapper {


    // 查询帖子
    List<Posts> list(PostQueryParam postQueryParam);
}
