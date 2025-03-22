package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.PageResult;
import ac.liverpool.forum.entity.PostQueryParam;
import ac.liverpool.forum.entity.Posts;
import ac.liverpool.forum.entity.PostsVo;
import ac.liverpool.forum.mapper.PostsMapper;
import ac.liverpool.forum.service.PostsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostsServiceImpl implements PostsService {
    @Autowired
    private PostsMapper postsMapper;


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

}
