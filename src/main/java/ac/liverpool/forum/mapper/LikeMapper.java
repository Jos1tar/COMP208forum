package ac.liverpool.forum.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    boolean isAlreadyLiked(@Param("userId") Long userId,
                           @Param("targetId") Long targetId,
                           @Param("type") String type);

    void like(@Param("userId") Long userId,
              @Param("targetId") Long targetId,
              @Param("type") String type);

    int countLikes(@Param("targetId") Long targetId,
                   @Param("type") String type);
}
