package ac.liverpool.forum.mapper;

import ac.liverpool.forum.entity.VO.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MessageMapper {
    /**
     * insertion
     * @param message
     * @return rows affected
     */
    int insert(Message message);

    /**
     * update message
     * @param message
     * @return rows affected
     */
    int update(Message message);

    /**
     * Retrieve messages between two users
     * @param userId
     * @param targetUserId
     * @param offset
     * @param limit
     * @return List of messages
     */
    List<Message> getMessages(@Param("userId") Long userId, 
                             @Param("targetUserId") Long targetUserId,
                             @Param("offset") int offset, 
                             @Param("limit") int limit);

    /**
     * Count the number of messages between two users
     * @param userId
     * @param targetUserId
     * @return message count
     */
    int countMessages(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);

    /**
     * mark messages as read
     * @param userId
     * @param user1Id
     * @param user2Id
     * @return rows affected
     */
    int markAsRead(@Param("userId") Long userId, 
                  @Param("user1Id") Long user1Id, 
                  @Param("user2Id") Long user2Id);
} 