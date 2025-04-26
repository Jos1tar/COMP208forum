package ac.liverpool.forum.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationVO {

    private Long conversationId;

    private Long userId;

    private String username;

    private String nickname;

    private String avatar;

    private String lastMessage;

    private LocalDateTime lastMessageTime;

    private Integer unreadCount;
} 