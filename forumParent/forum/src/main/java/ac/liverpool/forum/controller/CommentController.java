package ac.liverpool.forum.controller;


import ac.liverpool.forum.entity.CommentQueryParam;
import ac.liverpool.forum.entity.Comments;
import ac.liverpool.forum.entity.PageResult;
import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.service.CommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    //查询帖子的父评论，parentid=null; Long postId;必须 - 要查询的帖子ID
    //注意此处的parentId=null为父评论，但是我不小心把开始几个测试用例的parentId写成了0
    @GetMapping("/commentList")
        public Result getComment(CommentQueryParam commentQueryParam) {
        log.info("查询请求参数： {}", commentQueryParam);
        PageResult pageResult = commentsService.getComment(commentQueryParam);
        return Result.success(pageResult);
    }

    /**
     * 获取指定父评论的子评论列表
     * @param parentId 父评论ID
     * @return 子评论列表
     */
    @GetMapping("/childComments/{parentId}")
    public Result getChildComments(@PathVariable Long parentId) {
        log.info("获取父评论{}的子评论", parentId);
        List<Comments> childComments = commentsService.getChildComments(parentId);
        return Result.success(childComments);
    }

    /**
     * 新增评论
     * @param comment 评论实体
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result addComment(@RequestBody Comments comment) {
        log.info("新增评论: {}", comment);
        commentsService.addComment(comment);
        return Result.success();
    }


}
