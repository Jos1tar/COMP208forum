package ac.liverpool.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequest {
    private Long userId;
    private Long targetId;
    private String type;  // "post" 或 "comment"
}