package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.Result;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.mapper.LikeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LikeServiceImpl implements ac.liverpool.forum.service.LikeService {
    @Autowired
    private LikeMapper likeMapper;

    @Override
    public Result like(Long userId, Long targetId, String type) {
        // 检查是否已点赞
        if (likeMapper.isAlreadyLiked(userId, targetId, type)) {
            return Result.error(AppHttpCodeEnum.REPEAT_LIKE);
        }

        // 执行点赞操作
        likeMapper.like(userId, targetId, type);

        // 获取更新后的点赞数
        int likeCount = likeMapper.countLikes(targetId, type);

        return Result.success(likeCount);
    }
}
