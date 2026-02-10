package egovframework.com.ites.user;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

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
	@GetMapping("/api/user/find")
	public UserVO findUser(String orgName, String userName) {
	    return userService.findUserInfo(orgName, userName);
	}

	@SuppressWarnings("unused")
	private String cleanXSS(String value) {
	    value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
	    value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
	    value = value.replaceAll("'", "& #39;");
	    value = value.replaceAll("eval\\((.*)\\)", "");
	    value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
	    value = value.replaceAll("script", "");
	    return value;
	}
	
	
 }