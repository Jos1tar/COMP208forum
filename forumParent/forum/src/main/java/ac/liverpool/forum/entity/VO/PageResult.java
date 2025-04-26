package ac.liverpool.forum.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 *
 * Encapsulate pagination results
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total; //Total number of records
    private List<T> rows;// The data on the current page
}