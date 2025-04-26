package ac.liverpool.forum.service;

import ac.liverpool.forum.entity.dto.CommentQueryParam;
import ac.liverpool.forum.entity.VO.PageResult;
import ac.liverpool.forum.entity.Comments;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CommentsService {


    PageResult getComment(CommentQueryParam commentQueryParam);


    List<Comments> getChildComments(Long parentId);

    void addComment(Comments comment);







}

