package ac.liverpool.forum.entity.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PostQueryParam {
    private Integer page = 1; //
    private Integer pageSize = 10; // Number of records displayed per page
    private String keyword; //Post Title Keywords
    private Boolean orderByLikes = false; // Sort

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
}
