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
 * @since 2025-04-01 17:19:55
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Likes  {

    private Long id;

    private Long userId;

    private Long targetId;

    private String type;

    private Date createdAt;


}

