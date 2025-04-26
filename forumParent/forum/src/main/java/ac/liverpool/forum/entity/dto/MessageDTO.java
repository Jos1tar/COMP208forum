package ac.liverpool.forum.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private Long userId;


    private Long receiverId;

    private String content;
} 