package ac.liverpool.forum.controller;


import ac.liverpool.forum.entity.LikeRequest;
import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("like")
public class LikeController {
    @Autowired
    private LikeService likeService;
    @PostMapping
    public Result like(@RequestBody LikeRequest likeRequest) {
        return likeService.like(
                likeRequest.getUserId(),
                likeRequest.getTargetId(),
                likeRequest.getType()
        );
    }
}
