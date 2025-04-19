package ac.liverpool.forum.controller;



import ac.liverpool.forum.entity.*;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.service.PostsService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.Serializable;
import java.util.List;

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
    /**
     * 服务对象
     */
    @Autowired
    private PostsService postsService;

    //分页查询帖子
    @GetMapping("/list")
    public Result getPosts(PostQueryParam postQueryParam) {
        log.info("查询请求参数： {}", postQueryParam);
        PageResult pageResult = postsService.getPosts(postQueryParam);
        return Result.success(pageResult);
    }

    //点击阅读全文展示全部内容，根据帖子ID查询帖子详情
    @GetMapping("/{id}")
    public Result getPostsById(@PathVariable Long id) {
        Posts posts = postsService.getPostById(id);
        return Result.success(posts);
    }

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

