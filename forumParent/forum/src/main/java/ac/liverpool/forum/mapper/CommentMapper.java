package ac.liverpool.forum.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import ac.liverpool.forum.entity.Comments;
import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comments> listByPostId(@Param("postId") Long postId, @Param("parentId") Long parentId);

    /**
     * 根据父评论ID查询所有子评论
     * @param parentId 父评论ID
     * @return 子评论列表
     */
    List<Comments> listByParentId(@Param("parentId") Long parentId);

    void addComment(Comments comment);
}
