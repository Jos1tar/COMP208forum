package ac.liverpool.forum.controller;



import ac.liverpool.forum.entity.PageResult;
import ac.liverpool.forum.entity.PostQueryParam;
import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.entity.Posts;
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




}

