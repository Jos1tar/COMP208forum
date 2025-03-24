package ac.liverpool.forum.mapper;

import ac.liverpool.forum.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {

    /**
     * 根据用户名和密码查询员工信息
     */
    @Select("select * from users where username = #{username} and password = #{password}")
    Users getUsernameAndPassword(Users emp);
}
