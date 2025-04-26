package ac.liverpool.forum.service;

import ac.liverpool.forum.entity.dto.MessageDTO;
import ac.liverpool.forum.entity.VO.Result;

/**

 */
public interface MessageService {

    Result sendMessage(Long senderId, MessageDTO messageDTO);


    Result getMessages(Long userId, Long targetUserId, Integer page, Integer pageSize);


    Result getConversations(Long userId);


    Result markAsRead(Long userId, Long conversationId);


    Result getUnreadCount(Long userId);


    Result deleteConversation(Long userId, Long conversationId);
} 