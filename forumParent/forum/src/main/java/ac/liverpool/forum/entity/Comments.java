package ac.liverpool.forum.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 *
 *
 * @author makejava
 * @since 2025-03-23 17:51:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments  {

    private Long id;
//
    private Long postId;

    private Long userId;

    private Long parentId;

    private String content;

    private Integer likes;

    private Date createdAt;

    private Integer isDeleted;

    

}

