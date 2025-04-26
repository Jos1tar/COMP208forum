package ac.liverpool.forum.controller;

import ac.liverpool.forum.entity.dto.MessageDTO;
import ac.liverpool.forum.entity.VO.Result;
import ac.liverpool.forum.enums.AppHttpCodeEnum;
import ac.liverpool.forum.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     *send message
     */
    @PostMapping("/send")
    public Result sendMessage(HttpServletRequest request, @RequestBody MessageDTO messageDTO) {
        // get userId from request attribute
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error(AppHttpCodeEnum.NEED_LOGIN);
        }
        return messageService.sendMessage(userId, messageDTO);
    }

    /**
     * get message list of the target user
     */
    @GetMapping("/list/{targetUserId}")
    public Result getMessages(@RequestParam Long userId,
                              @PathVariable Long targetUserId,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer pageSize) {


        if (userId == null) {
            return Result.error(AppHttpCodeEnum.NEED_LOGIN);
        }
        return messageService.getMessages(userId, targetUserId, page, pageSize);
    }

    /**
     * get conversation list of one user
     */
    @GetMapping("/conversations")
    public Result getConversations(@RequestParam Long userId) {


        if (userId == null) {
            return Result.error(AppHttpCodeEnum.NEED_LOGIN);
        }
        return messageService.getConversations(userId);
    }

    /**
     * mark message as read
     */
    @PutMapping("/read/{conversationId}/{userId}")
    public Result markAsRead(@PathVariable Long conversationId,@PathVariable Long userId) {

        if (userId == null) {
            return Result.error(AppHttpCodeEnum.NEED_LOGIN);
        }
        return messageService.markAsRead(conversationId, userId);
    }

    /**
     *get unread message count
     */
    @GetMapping("/unread")
    public Result getUnreadCount(HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error(AppHttpCodeEnum.NEED_LOGIN);
        }
        return messageService.getUnreadCount(userId);
    }

    /**
     * delete conversation
     */
    @DeleteMapping("/{conversationId}")
    public Result deleteConversation(HttpServletRequest request, @PathVariable Long conversationId) {

        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error(AppHttpCodeEnum.NEED_LOGIN);
        }
        return messageService.deleteConversation(userId, conversationId);
    }
} 