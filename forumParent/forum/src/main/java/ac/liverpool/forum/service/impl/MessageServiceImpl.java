package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.VO.ConversationVO;
import ac.liverpool.forum.entity.dto.MessageDTO;
import ac.liverpool.forum.entity.VO.MessageVO;
import ac.liverpool.forum.entity.Conversation;
import ac.liverpool.forum.entity.VO.Message;
import ac.liverpool.forum.entity.VO.PageResult;
import ac.liverpool.forum.entity.VO.Result;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.mapper.ConversationMapper;
import ac.liverpool.forum.mapper.MessageMapper;
import ac.liverpool.forum.mapper.UsersMapper;
import ac.liverpool.forum.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional
    public Result sendMessage(Long senderId, MessageDTO messageDTO) {
        // check parameters
        if (senderId == null || messageDTO.getReceiverId() == null || messageDTO.getContent() == null) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR);
        }

        Long receiverId = messageDTO.getReceiverId();
        
        // Check if the recipient exists
        Users receiver = usersMapper.getUserById(receiverId);
        if (receiver == null) {
            return Result.error(AppHttpCodeEnum.USER_NOT_EXIST);
        }

        // not allow to send message to self
        if (senderId.equals(receiverId)) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR, "not allow to send message to self");
        }

        // look up conversation or create a new one
        Conversation conversation = conversationMapper.findConversation(senderId, receiverId);
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setUser1Id(Math.min(senderId, receiverId));
            conversation.setUser2Id(Math.max(senderId, receiverId));
            conversation.setLastMessage(messageDTO.getContent());
            conversation.setLastMessageTime(LocalDateTime.now());
            conversation.setUnreadCountUser1(senderId.equals(conversation.getUser1Id()) ? 0 : 1);
            conversation.setUnreadCountUser2(senderId.equals(conversation.getUser2Id()) ? 0 : 1);
            conversation.setCreateTime(LocalDateTime.now());
            conversation.setUpdateTime(LocalDateTime.now());
            conversation.setIsDeleted(0);
            conversationMapper.insert(conversation);
        } else {
            // update conversation
            conversation.setLastMessage(messageDTO.getContent());
            conversation.setLastMessageTime(LocalDateTime.now());
            //update unread count
            if (senderId.equals(conversation.getUser1Id())) {
                conversation.setUnreadCountUser2(conversation.getUnreadCountUser2() + 1);
            } else {
                conversation.setUnreadCountUser1(conversation.getUnreadCountUser1() + 1);
            }
            conversation.setUpdateTime(LocalDateTime.now());
            conversationMapper.update(conversation);
        }

        // create message
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(messageDTO.getContent());
        message.setIsRead(0);
        message.setCreateTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        message.setIsDeleted(0);
        messageMapper.insert(message);

        return Result.success();
    }

    @Override
    public Result getMessages(Long userId, Long targetUserId, Integer page, Integer pageSize) {
        if (userId == null || targetUserId == null) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR);
        }

        // pagination parameters
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 20;
        }

        // lookup messages
        List<Message> messages = messageMapper.getMessages(userId, targetUserId, (page - 1) * pageSize, pageSize);
        int total = messageMapper.countMessages(userId, targetUserId);

        // lookup users
        Users user = usersMapper.getUserById(userId);
        Users targetUser = usersMapper.getUserById(targetUserId);
        
        if (user == null || targetUser == null) {
            return Result.error(AppHttpCodeEnum.USER_NOT_EXIST);
        }

        // convert messages to MessageVO
        List<MessageVO> messageVOs = new ArrayList<>();
        for (Message message : messages) {
            MessageVO vo = new MessageVO();
            vo.setId(message.getId());
            vo.setSenderId(message.getSenderId());
            vo.setReceiverId(message.getReceiverId());
            vo.setContent(message.getContent());
            vo.setIsRead(message.getIsRead());
            vo.setCreateTime(message.getCreateTime());
            
            // set sender and receiver info
            if (message.getSenderId().equals(userId)) {
                vo.setSenderUsername(user.getUsername());
                vo.setSenderNickname(user.getNickname());
                vo.setSenderAvatar(user.getAvatar());
                vo.setReceiverUsername(targetUser.getUsername());
                vo.setReceiverNickname(targetUser.getNickname());
                vo.setReceiverAvatar(targetUser.getAvatar());
                vo.setIsMine(true);
            } else {
                vo.setSenderUsername(targetUser.getUsername());
                vo.setSenderNickname(targetUser.getNickname());
                vo.setSenderAvatar(targetUser.getAvatar());
                vo.setReceiverUsername(user.getUsername());
                vo.setReceiverNickname(user.getNickname());
                vo.setReceiverAvatar(user.getAvatar());
                vo.setIsMine(false);
                
                // mark as read if not already
                if (message.getIsRead() == 0) {
                    message.setIsRead(1);
                    message.setUpdateTime(LocalDateTime.now());
                    messageMapper.update(message);
                }
            }
            
            messageVOs.add(vo);
        }

        // update conversation unread count
        Conversation conversation = conversationMapper.findConversation(userId, targetUserId);
        if (conversation != null) {
            if (userId.equals(conversation.getUser1Id())) {
                conversation.setUnreadCountUser1(0);
            } else {
                conversation.setUnreadCountUser2(0);
            }
            conversation.setUpdateTime(LocalDateTime.now());
            conversationMapper.update(conversation);
        }

        // create page result
        PageResult<MessageVO> pageResult = new PageResult<>();
        pageResult.setRows(messageVOs);
        pageResult.setTotal((long) total);
        
        return Result.success(pageResult);
    }

    @Override
    public Result getConversations(Long userId) {
        if (userId == null) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR);
        }

        // Query reply list
        List<Conversation> conversations = conversationMapper.getConversations(userId);
        List<ConversationVO> conversationVOs = new ArrayList<>();
        
        for (Conversation conversation : conversations) {
            ConversationVO vo = new ConversationVO();
            vo.setConversationId(conversation.getId());
            
            // check if the user is part of the conversation
            Long targetUserId = userId.equals(conversation.getUser1Id()) ? 
                                conversation.getUser2Id() : conversation.getUser1Id();
            
            // check the target user info
            Users targetUser = usersMapper.getUserById(targetUserId);
            if (targetUser == null) {
                continue;
            }
            
            vo.setUserId(targetUserId);
            vo.setUsername(targetUser.getUsername());
            vo.setNickname(targetUser.getNickname());
            vo.setAvatar(targetUser.getAvatar());
            vo.setLastMessage(conversation.getLastMessage());
            vo.setLastMessageTime(conversation.getLastMessageTime());
            
            // set unread count
            if (userId.equals(conversation.getUser1Id())) {
                vo.setUnreadCount(conversation.getUnreadCountUser1());
            } else {
                vo.setUnreadCount(conversation.getUnreadCountUser2());
            }
            
            conversationVOs.add(vo);
        }
        
        return Result.success(conversationVOs);
    }

    @Override
    @Transactional
    public Result markAsRead(Long userId, Long conversationId) {
        if (userId == null || conversationId == null) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR);
        }

        // lookup conversation
        Conversation conversation = conversationMapper.selectById(conversationId);
        if (conversation == null) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR, "conversation not found");
        }

        // make sure the user is part of the conversation
        if (!userId.equals(conversation.getUser1Id()) && !userId.equals(conversation.getUser2Id())) {
            return Result.error(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }

        // update unread count
        if (userId.equals(conversation.getUser1Id())) {
            conversation.setUnreadCountUser1(0);
        } else {
            conversation.setUnreadCountUser2(0);
        }
        conversation.setUpdateTime(LocalDateTime.now());
        conversationMapper.update(conversation);

        // mark messages as read
        messageMapper.markAsRead(userId, conversation.getUser1Id(), conversation.getUser2Id());

        return Result.success();
    }

    @Override
    public Result getUnreadCount(Long userId) {
        if (userId == null) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR);
        }

        // get unread count
        int unreadCount = conversationMapper.getUnreadCount(userId);
        
        return Result.success(unreadCount);
    }

    @Override
    @Transactional
    public Result deleteConversation(Long userId, Long conversationId) {
        if (userId == null || conversationId == null) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR);
        }

        // lookup conversation
        Conversation conversation = conversationMapper.selectById(conversationId);
        if (conversation == null) {
            return Result.error(AppHttpCodeEnum.PARAMETER_ERROR, "conversation not found");
        }


        if (!userId.equals(conversation.getUser1Id()) && !userId.equals(conversation.getUser2Id())) {
            return Result.error(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }

        // mark conversation as deleted
        conversation.setIsDeleted(1);
        conversation.setUpdateTime(LocalDateTime.now());
        conversationMapper.update(conversation);

        return Result.success();
    }
} 