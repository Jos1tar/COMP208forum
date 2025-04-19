package ac.liverpool.forum.entity;

import java.beans.Transient;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 帖子表(Posts)表实体类
 *
 * @author makejava
 * @since 2025-03-22 14:26:15
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
    private Long id;
    private Long userId;
    private String username; // 新增字段
    private String title;
    private String content;
    private Integer views = 0;
    private Integer likes = 0;
    private Integer comments = 0;
    private Date createdAt;
    private Integer isDeleted = 0;

    // 非数据库字段，用于返回图片列表
    private List<String> imageUrls;
}
