package ac.liverpool.forum.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * present message entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    // message ID
    private Long id;

    private Long senderId;
    // 接收者ID
    private Long receiverId;

    private String content;
    // Has it been read (0-unread, 1-read)
    private Integer isRead;
    //
    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    // Whether to delete (0-not deleted, 1-deleted)
    private Integer isDeleted;
} 