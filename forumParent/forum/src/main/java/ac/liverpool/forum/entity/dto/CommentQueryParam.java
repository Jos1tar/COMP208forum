package ac.liverpool.forum.entity.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CommentQueryParam {
    private Long postId;
    private Integer page = 1; //
    private Integer pageSize = 10;
    private Long parentId;


}

