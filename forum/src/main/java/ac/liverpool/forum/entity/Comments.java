package ac.liverpool.forum.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 评论表(Comments)表实体类
 *
 * @author makejava
 * @since 2025-03-23 17:51:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments  {
//评论ID
    private Long id;
//关联帖子ID
    private Long postId;
//评论用户ID
    private Long userId;
//父级评论ID（楼中楼）
    private Long parentId;
//评论内容
    private String content;
//点赞数
    private Integer likes;
//评论时间
    private Date createdAt;
//软删除标记
    private Integer isDeleted;
    

}

