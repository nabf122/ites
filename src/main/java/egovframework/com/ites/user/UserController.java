package egovframework.com.ites.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.ites.user.service.UserService;
import egovframework.com.ites.user.service.UserVO;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * 사용자 찾기
	 */
	@GetMapping("/find")
	@ResponseBody
	public UserVO findUser(UserVO userVO) {
	    return userService.findUserInfo(userVO.getOrgName(), userVO.getUserName());
	}
 }