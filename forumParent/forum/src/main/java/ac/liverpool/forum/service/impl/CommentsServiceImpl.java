package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.dto.CommentQueryParam;
import ac.liverpool.forum.entity.Comments;
import ac.liverpool.forum.entity.VO.PageResult;
import ac.liverpool.forum.mapper.CommentMapper;
import ac.liverpool.forum.service.CommentsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Comments Table Service Implementation Class
 *
 * @author makejava
 * @since 2025-03-23 17:51:13
 */
@Slf4j
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PageResult getComment(CommentQueryParam commentQueryParam) {
        log.info("Query request parameters {}", commentQueryParam);

        // start paging
        PageHelper.startPage(commentQueryParam.getPage(), commentQueryParam.getPageSize());
        // Check the parent comments under this post, pay attention to the parentId=null here
        List<Comments> comments = commentMapper.listByPostId(commentQueryParam.getPostId(),null );

        // Encapsulate pagination data using PageInfo
        Page<Comments> p =  (Page<Comments>)comments;

        return new PageResult(p.getTotal(), comments);
    }

    @Override
    public List<Comments> getChildComments(Long parentId) {
        log.info("Search for child comments with parent comment ID={}", parentId);
        return commentMapper.listByParentId(parentId);
    }

    @Override
    public void addComment(Comments comment) {
        log.info("New comment: {}", comment);
        commentMapper.addComment(comment);
    }
}

