package ac.liverpool.forum.service;

import ac.liverpool.forum.entity.CommentQueryParam;
import ac.liverpool.forum.entity.PageResult;
import ac.liverpool.forum.entity.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import ac.liverpool.forum.entity.Comments;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comments)表服务接口
 *
 * @author makejava
 * @since 2025-03-23 17:51:13
 */
@Service
public interface CommentsService {


    /**
     * 根据postsID查询评论
     *

     */
    PageResult getComment(CommentQueryParam commentQueryParam);

    /**
     * 获取指定父评论的所有子评论
     * @param parentId 父评论ID
     * @return 子评论列表
     */
    List<Comments> getChildComments(Long parentId);

    void addComment(Comments comment);







}

