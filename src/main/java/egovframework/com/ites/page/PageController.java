package egovframework.com.ites.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/user/main")
    public String userPage() { return "user/main"; }
	
	@GetMapping("/admin/list")
    public String adminPage() { return "admin/list"; }
}

