package ac.liverpool.forum.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PostQueryParam {
    private Integer page = 1; // 页码
    private Integer pageSize = 10; // 每页展示记录数
    private String keyword; // 帖子标题关键字
    private Boolean orderByLikes = false; // 改为按点赞数排序

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begin; // 查询起始时间

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end; // 查询结束时间
}
