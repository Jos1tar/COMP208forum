package ac.liverpool.forum.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 点赞表(Likes)表实体类
 *
 * @author makejava
 * @since 2025-04-01 17:19:55
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes  {
//点赞记录ID
    private Long id;
//点赞用户ID
    private Long userId;
//被点赞对象ID（帖子/评论）
    private Long targetId;
//点赞类型
    private String type;
//点赞时间
    private Date createdAt;


}

