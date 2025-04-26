package ac.liverpool.forum.controller;



import ac.liverpool.forum.entity.dto.PostCreateRequest;
import ac.liverpool.forum.entity.dto.PostQueryParam;
import ac.liverpool.forum.entity.*;
import ac.liverpool.forum.entity.VO.PageResult;
import ac.liverpool.forum.entity.VO.Result;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.service.PostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 帖子表(Posts)表控制层
 *
 * @author makejava
 * @since 2025-03-22 14:26:15
 */
@Slf4j
@RestController
@RequestMapping("posts")
public class PostsController  {

    @Autowired
    private PostsService postsService;

    //Paginated queries
    @GetMapping("/list")
    public Result getPosts(PostQueryParam postQueryParam) {
        log.info("查询请求参数： {}", postQueryParam);
        PageResult pageResult = postsService.getPosts(postQueryParam);
        return Result.success(pageResult);
    }

    //click the post to view the details
    @GetMapping("/{id}")
    public Result getPostsById(@PathVariable Long id) {
        Posts posts = postsService.getPostById(id);
        return Result.success(posts);
    }

    //create a new post
    @PostMapping
    public Result createPost( @RequestBody PostCreateRequest request) {
        try {
            Posts post = postsService.createPost(request);
            return Result.success(post);
        } catch (Exception e) {
            return Result.error(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }



}

