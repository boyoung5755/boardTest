package innovationT.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AuthenticationFilter implements Filter {
	
	
	private List<String> excludedUrls;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		/*
		String excludePattern = filterConfig.getInitParameter("excludedUrls");
		
		excludedUrls = Arrays.asList(excludePattern.split(",")); */
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		log.info("==========Authentication 필터 시작!==========");
		
		String path =((HttpServletRequest)request).getServletPath();
		
		if(! excludedUrls.contains(path)) {
			 HttpServletResponse httpResponse = (HttpServletResponse) response;
	         httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "로그인 후 이용가능합니다.");
		}
		
		chain.doFilter(request, response);
		log.info("==========Authentication 필터 종료!==========");
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
