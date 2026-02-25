package egovframework.com.ites.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.ites.user.service.UserService;
import egovframework.com.ites.user.service.UserVO;
import egovframework.com.ites.util.service.Sha256Util;

@Controller
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	UserService userService;
	
	// 로그인 화면
	@GetMapping("/login")
    public String loginPage() {
		return "auth/login";
    }

	// 로그인에 대한 처리
	@PostMapping("/login")
    public String doLogin(@RequestParam String userId,
    					@RequestParam String userPw,
    					HttpServletRequest request) {

		// 로그인 사용자 검증
		// 넘어온 패스워드를 SHA256으로 암호화
		String shaPw = Sha256Util.sha256Hex(userPw);
		
		// userId와 shaPw로 DB에서 사용자 조회
		UserVO user = userService.loginUser(userId, shaPw);
		if(user == null) {
			request.setAttribute("msg", "아이디 또는 비밀번호가 올바르지 않습니다.");
			return "auth/login";
		}
		
		// 기존 세션 무효화
		HttpSession old = request.getSession(false);
		if(old != null) old.invalidate();
		
		// 새로운 세션 생성/저장
		HttpSession session = request.getSession(true);
		session.setAttribute("AUTH", true);
		session.setAttribute("LOGIN_USER", user.getUserId());
		session.setAttribute("ROLE", user.getRole());
		session.setAttribute("LOGIN_DT", System.currentTimeMillis());
		
		// 세션 타임 아웃: 초 단위
		session.setMaxInactiveInterval(30 * 60); // 30분
		
		userService.updateLastLoginDt(user.getUserId());
		
		// 권한별 다른 페이지로 이동
		if("ADMIN".equalsIgnoreCase(user.getRole())) {
			return "redirect:/admin/list";
		} else {
			return "redirect:/user/main";
		}
		
    }
	
	// 로그아웃
	@GetMapping("/logout")
    public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null) session.invalidate(); // 세션 해지 
		return "redirect:/auth/login";
    }
}

