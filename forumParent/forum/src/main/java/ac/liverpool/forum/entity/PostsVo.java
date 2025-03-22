package ac.liverpool.forum.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsVo {
    private Long id;          // 帖子ID
    private String title;       // 帖子标题
    private String content;     // 帖子内容
    private Date createdAt;     // 创建时间
    private Integer views;      // 浏览量
    private String username;    // 发帖用户名称
    //点赞数
    private Integer likes;
    //评论数
    private Integer comments;
}
