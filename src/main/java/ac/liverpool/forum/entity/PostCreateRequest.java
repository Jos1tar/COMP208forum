package ac.liverpool.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    private String title;


    private String content;


    private Long userId;

    // 图片URL列表
    private List<String> imageUrls;
}
