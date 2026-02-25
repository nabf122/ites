package egovframework.com.ites.user.mapper;

import org.apache.ibatis.annotations.Param;

import egovframework.com.ites.user.service.UserVO;

public interface UserMapper {

	UserVO selectUserInfo(
		    @Param("orgName") String orgName,
		    @Param("userName") String userName
		);
	
	UserVO selectLoginUser(
		    @Param("userId") String userId,
		    @Param("userPw") String userPw
		);

	void updateLastLoginDt(
			@Param("userId") String userId
		);
}
