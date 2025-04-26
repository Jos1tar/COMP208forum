package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.VO.Result;
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
        // Check if it has been liked
        if (likeMapper.isAlreadyLiked(userId, targetId, type)) {
            return Result.error(AppHttpCodeEnum.REPEAT_LIKE);
        }

        // operation of like
        likeMapper.like(userId, targetId, type);

        // get the number of likes updated
        int likeCount = likeMapper.countLikes(targetId, type);

        return Result.success(likeCount);
    }
}
