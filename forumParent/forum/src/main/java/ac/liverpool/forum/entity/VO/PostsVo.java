package ac.liverpool.forum.entity.VO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsVo {
    private Long id;
    private String title;
    private String content;
    private Date createdAt;
    private Integer views;
    private String username;

    private Integer likes;
    // number of comments
    private Integer comments;
}
