package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.CommentQueryParam;
import ac.liverpool.forum.entity.Comments;
import ac.liverpool.forum.entity.PageResult;
import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.mapper.CommentMapper;
import ac.liverpool.forum.service.CommentsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;

/**
 * 评论表(Comments)表服务实现类
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
        log.info("查询请求参数： {}", commentQueryParam);

        // 开启分页
        PageHelper.startPage(commentQueryParam.getPage(), commentQueryParam.getPageSize());
        // 查询该帖子下的父评论,注意此处的parentId=null，但是我不小心把开始几个测试用例的parentId写成了0
        List<Comments> comments = commentMapper.listByPostId(commentQueryParam.getPostId(),null );

        // 使用 PageInfo 封装分页数据
        Page<Comments> p =  (Page<Comments>)comments;

        return new PageResult(p.getTotal(), comments);
    }

    @Override
    public List<Comments> getChildComments(Long parentId) {
        log.info("查询父评论ID={}的子评论", parentId);
        return commentMapper.listByParentId(parentId);
    }

    @Override
    public void addComment(Comments comment) {
        log.info("新增评论: {}", comment);
        commentMapper.addComment(comment);
    }
}

