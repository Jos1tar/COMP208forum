package ac.liverpool.forum.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Message View Object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVO {
    // message ID
    private Long id;

    private Long senderId;

    private String senderUsername;

    private String senderNickname;

    private String senderAvatar;

    private Long receiverId;

    private String receiverUsername;

    private String receiverNickname;

    private String receiverAvatar;

    private String content;

    private Integer isRead;

    private LocalDateTime createTime;

    //Is it sent by the current user
    private Boolean isMine;
} 