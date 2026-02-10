package egovframework.com.ites.user.mapper;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.com.ites.user.service.UserVO;

@Mapper
public interface UserMapper {

	UserVO selectUserInfo(String orgName, String userName);
}
