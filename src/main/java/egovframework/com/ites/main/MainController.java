package egovframework.com.ites.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/main")
    public String main() {
        return "main/main";
    }
	
	@RequestMapping("/")
    public String home() {
        return "redirect:/main.do";
    }
}

