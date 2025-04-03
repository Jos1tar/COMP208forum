package ac.liverpool.forum.mapper;

import ac.liverpool.forum.entity.Posts;
import ac.liverpool.forum.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsersMapper {

    /**
     * 根据用户名和密码查询员工信息
     */
    @Select("select * from users where username = #{username} and password = #{password}")
    Users getUsernameAndPassword(Users emp);

    //根据id查询用户信息
    @Select("select * from users where id = #{id}")
    Users getUserById(Long id);

    @Select("select * from posts where user_id = #{userId}")
    List<Posts> getPostsByUserId(Long userId);

    //根据id查询用户名称
    @Select("select username from users where id = #{id}")
    String getUsernameById(Long id);

    boolean existsByUsername(@Param("username") String username,
                             @Param("excludeUserId") Long excludeUserId);
    int updateUserInfo(@Param("user") Users user);
}
