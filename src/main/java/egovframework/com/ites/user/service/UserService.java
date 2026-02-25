package egovframework.com.ites.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.ites.user.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
    private final UserMapper userMapper;
    
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserVO findUserInfo(String orgName, String userName) {
        return userMapper.selectUserInfo(orgName, userName);
    }
    
    public UserVO loginUser(String userId, String userPw) {
        return userMapper.selectLoginUser(userId, userPw);
    }

	public void updateLastLoginDt(String userId) {
		// TODO Auto-generated method stub
		userMapper.updateLastLoginDt(userId);
		
	}
}

