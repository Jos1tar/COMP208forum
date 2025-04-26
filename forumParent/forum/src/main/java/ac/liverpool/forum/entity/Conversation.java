package ac.liverpool.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 聊天会话实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {

    private Long id;

    private Long user1Id;

    private Long user2Id;

    private String lastMessage;

    private LocalDateTime lastMessageTime;

    private Integer unreadCountUser1;

    private Integer unreadCountUser2;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDeleted;
} 