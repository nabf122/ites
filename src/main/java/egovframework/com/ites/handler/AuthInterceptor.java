package egovframework.com.ites.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private final boolean adminOnly;

    public AuthInterceptor(boolean adminOnly) {
        this.adminOnly = adminOnly;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String uri = req.getRequestURI();

        // 로그인/정적리소스는 통과
        if (uri.startsWith(req.getContextPath() + "/auth")) return true;
        if (uri.contains("/resources/")) return true;

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            res.sendRedirect(req.getContextPath() + "/auth/login");
            return false;
        }

        if (adminOnly) {
            String role = (String) session.getAttribute("ROLE");
            if (!"ADMIN".equalsIgnoreCase(role)) {
                res.sendRedirect(req.getContextPath() + "/user/main");
                return false;
            }
        }

        return true;
    }
}