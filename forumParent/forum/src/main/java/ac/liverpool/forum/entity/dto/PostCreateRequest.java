package ac.liverpool.forum.entity.dto;

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

    // List of Image URLs
    private List<String> imageUrls;
}
