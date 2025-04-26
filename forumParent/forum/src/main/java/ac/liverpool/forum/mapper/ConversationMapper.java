package ac.liverpool.forum.mapper;

import ac.liverpool.forum.entity.Conversation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Session Mapper interface
 */
@Mapper
public interface ConversationMapper {
    /**
     * 插入会话
     * @param conversation
     * @return Affects the number of rows
     */
    int insert(Conversation conversation);


    int update(Conversation conversation);

    /**
     * Query sessions based on ID

     */
    Conversation selectById(Long id);

    /**
     * Find a conversation between two users
     * @param userId1
     * @param userId2
     */
    Conversation findConversation(@Param("userId1") Long userId1, @Param("userId2") Long userId2);

    List<Conversation> getConversations(Long userId);

    /**
     * Retrieve the number of unread messages from the user

     */
    int getUnreadCount(Long userId);
} 