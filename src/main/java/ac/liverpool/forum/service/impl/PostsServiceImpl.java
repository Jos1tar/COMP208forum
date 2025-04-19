package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.*;
import ac.liverpool.forum.mapper.PostsMapper;
import ac.liverpool.forum.mapper.UsersMapper;
import ac.liverpool.forum.service.PostsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostsServiceImpl implements PostsService {
    @Autowired
    private PostsMapper postsMapper;

    @Autowired
    private UsersMapper usersMapper;


    // 分页查询加条件查询的实现
    @Override
    public PageResult getPosts(PostQueryParam postQueryParam) {
        // 开启分页
        PageHelper.startPage(postQueryParam.getPage(), postQueryParam.getPageSize());
        // 查询数据
        List<Posts> posts = postsMapper.list(postQueryParam);
        // 将 Posts 转换为 PostsVo
        List<PostsVo> postsVoList = posts.stream()
                .map(postList -> {
                    PostsVo postsVo = new PostsVo();
                    BeanUtils.copyProperties(postList, postsVo); // Bean 拷贝
                    return postsVo;
                })
                .collect(Collectors.toList());

        // 获取分页信息
        Page<Posts> p = (Page<Posts>) posts;

        // 返回分页结果
        return new PageResult(p.getTotal(), postsVoList);
    }

    // 根据帖子ID查询帖子详情
    @Override
    public Posts getPostById(Long id) {
        return postsMapper.getPostById(id);
    }

    // 创建帖子
    @Override
    public Posts createPost(PostCreateRequest request) {
        // 1. 获取用户名
        String username = usersMapper.getUsernameById(request.getUserId());
        if (username == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 创建帖子
        Posts post = new Posts();
        post.setUserId(request.getUserId());
        post.setUsername(username); // 设置用户名
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(new Date());

        postsMapper.insertPost(post);

        // 3. 保存图片
        if (request.getImageUrls() != null && !request.getImageUrls().isEmpty()) {
            postsMapper.insertPostImages(post.getId(), request.getImageUrls());
            post.setImageUrls(request.getImageUrls());
        }

        return post;
    }
}
