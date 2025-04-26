package ac.liverpool.forum.service;

import ac.liverpool.forum.entity.VO.Result;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {

    Result like(Long userId, Long targetId, String type);
}
