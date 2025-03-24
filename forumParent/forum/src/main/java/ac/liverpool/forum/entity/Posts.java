package ac.liverpool.forum.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


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
public class Posts  {
//帖子ID
    private Long id;
//发布用户ID
    private Long userId;
    private String username;   // 发帖人用户名
//帖子标题
    private String title;
//帖子内容
    private String content;
//浏览次数
    private Integer views;
//点赞数
    private Integer likes;
//评论数
    private Integer comments;
//发布时间
    private Date createdAt;
//软删除标记
    private Integer isDeleted;





}

