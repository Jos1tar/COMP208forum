package ac.liverpool.forum.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CommentQueryParam {
    private Long postId; // 帖子ID
    private Integer page = 1; // 页码
    private Integer pageSize = 10; // 每页展示记录数
    private Long parentId; // 父级评论ID


}

