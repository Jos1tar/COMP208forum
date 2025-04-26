package ac.liverpool.forum.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import ac.liverpool.forum.entity.Comments;
import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comments> listByPostId(@Param("postId") Long postId, @Param("parentId") Long parentId);

    /**
     * Retrieve all child comments based on the parent comment ID
     * @param parentId
     * @return List of sub comments
     */
    List<Comments> listByParentId(@Param("parentId") Long parentId);

    void addComment(Comments comment);
}
