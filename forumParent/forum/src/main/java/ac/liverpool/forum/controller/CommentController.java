package ac.liverpool.forum.controller;


import ac.liverpool.forum.entity.dto.CommentQueryParam;
import ac.liverpool.forum.entity.Comments;
import ac.liverpool.forum.entity.VO.PageResult;
import ac.liverpool.forum.entity.VO.Result;
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

    //Query the parent comments of the post, parentid=null;  Long postId; Required - Post ID to be queried
    //Note that parentId=null here is the parent comment
    // But I accidentally wrote the parentId of the first few test cases as 0
    @GetMapping("/commentList")
        public Result getComment(CommentQueryParam commentQueryParam) {
        log.info("params for the comment： {}", commentQueryParam);
        PageResult pageResult = commentsService.getComment(commentQueryParam);
        return Result.success(pageResult);
    }

    /**

     * Retrieve the list of child comments for the specific parent comments in real-time translation of document images 13/10000
     */
    @GetMapping("/childComments/{parentId}")
    public Result getChildComments(@PathVariable Long parentId) {
        log.info("get the sub comment of {}", parentId);
        List<Comments> childComments = commentsService.getChildComments(parentId);
        return Result.success(childComments);
    }

    /**
     new comment
     */
    @PostMapping("/add")
    public Result addComment(@RequestBody Comments comment) {
        log.info("new comment: {}", comment);
        commentsService.addComment(comment);
        return Result.success();
    }


}
