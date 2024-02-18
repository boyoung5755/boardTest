package innovationT.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Component
public class LoginFilter implements Filter {
	
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LoginFilter가 생성 됩니다.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("==========Login 필터 시작!==========");
        
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        
        HttpSession session = httpRequest.getSession();
        
        String loginName = (String) session.getAttribute("loginName");
        if (loginName != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "로그인 후 이용가능합니다.");
        }
        
        log.info("==========Login 필터 종료!==========");
    }

    @Override
    public void destroy() {
        log.info("LoginFilter가 사라집니다.");
    }
    
  
    
    
}