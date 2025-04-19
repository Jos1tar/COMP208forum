package ac.liverpool.forum.mapper;

import ac.liverpool.forum.entity.Posts;
import ac.liverpool.forum.entity.Users;
import org.apache.ibatis.annotations.*;

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

    // 新增注册相关方法
    @Insert("INSERT INTO users (username, password,  email, email_verified, " +
            "verification_code, verification_code_expire_time, create_time, update_time) " +
            "VALUES (#{username}, #{password},  #{email}, " +
            "#{emailVerified}, #{verificationCode}, #{verificationCodeExpireTime}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Users user);

    @Select("SELECT * FROM users WHERE email = #{email}")
    Users findByEmail(String email);

    @Select("SELECT * FROM users WHERE username = #{username}")
    Users findByUsername(String username);

    @Update("UPDATE users SET email_verified = true WHERE id = #{id}")
    int updateEmailVerified(Long id);

    @Update("UPDATE users SET verification_code = #{verificationCode}, verification_code_expire_time = #{verificationCodeExpireTime}, update_time = #{updateTime} WHERE email = #{email}")
    int updateVerificationCode(Users user);
}
