package innovationT.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomIntercepter implements HandlerInterceptor{
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("□□□□□□□□□□□□□□□□□□□□□□□□□□□ preHandle");
        // 컨트롤러 실행 전에 수행할 작업
		
		String role = (String)request.getSession().getAttribute("loginRole");
		
		//세션이 담긴 role이 admin 일때 관리자 페이지로 이동가능
		if("admin".equals(role)) {
			log.info("관리자로 로그인");
			//관리자페이지로 이동
			return true;
		}else{
	
			String warningMessage = "Y"; // 경고 메시지
			String contextPath = request.getContextPath();
			String redirectUrl = contextPath + "/login"; // 관리자 로그인 페이지 경로
			response.sendRedirect(redirectUrl + "?warning="+warningMessage);
			//response.sendRedirect(redirectUrl); // 리다이렉트
			
			
			return false;
		}
        
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 실행 후에 수행할 작업
    	
    	log.info("□□□□□□□□□□□□□□□□□□□□□□□□□□□ postHandle");
    	
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 응답 완료 후에 수행할 작업
    }
	

}
