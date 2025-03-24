package ac.liverpool.forum.service.impl;

import ac.liverpool.forum.entity.LoginInfo;
import ac.liverpool.forum.entity.Users;
import ac.liverpool.forum.mapper.UsersMapper;
import ac.liverpool.forum.service.UsersService;
import ac.liverpool.forum.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;
    @Override
    public LoginInfo login(Users emp) {
        Users usersLogin = usersMapper.getUsernameAndPassword(emp);
        if(usersLogin != null){
            //1. 生成JWT令牌
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("id", usersLogin.getId());
            dataMap.put("username", usersLogin.getUsername());

            String jwt = JwtUtils.generateJwt(dataMap);
            LoginInfo loginInfo = new LoginInfo(Math.toIntExact(usersLogin.getId()), usersLogin.getUsername(), jwt);
            return loginInfo;
        }
        return null;
    }
}
