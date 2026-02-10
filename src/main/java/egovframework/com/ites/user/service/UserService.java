package egovframework.com.ites.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import egovframework.com.ites.equipment.mapper.EquipmentMapper;
import egovframework.com.ites.user.mapper.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper = null;

    public UserVO findUserInfo(String orgName, String userName) {
        return userMapper.selectUserInfo(orgName, userName);
    }
}

