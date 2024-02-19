package innovationT.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {


	//로그인정보 입력폼 
	@GetMapping
	public String loginForm(Model model) {
		
		return "/login/loginHome";
	}
	
	@ResponseBody
	@PostMapping("auth")
	public Map<String, String> loginAuth(
		@RequestParam(value="username" ,required = true)String username
		, @RequestParam(value="password" ,required = true)String password
		, HttpServletRequest req
			) throws IOException{
		
		log.info("▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷▶▷로그인아이디 "+username +"비밀번호 "+password);
		
		HttpSession session = req.getSession();
		Map<String, String> map = new HashMap<String, String>();
		
		if(username.equals("admin")&& password.equals("admin")) {
			session.setAttribute("loginName", username);
			
			//인터셉터 연습하려고 담아놓음
			session.setAttribute("loginRole", "admin");
			
			map.put("role", "admin");
			map.put("success", "Y");
			
			//접속자수 카운팅
			loginCount(req);
			
		}else if(username.equals("user")&& password.equals("user")) {
			session.setAttribute("loginName", username);
			map.put("role", "user");
			map.put("success", "Y");
			
			//접속자수 카운팅
			loginCount(req);
			
		}else {
			map.put("success", "N");
		}	
		
		return map;
	}
	
	
	//접속자수 카운팅
	public void loginCount(HttpServletRequest req) {
		
		//application 예제
		Integer count = (Integer)req.getServletContext().getAttribute("accessCounter"); 
		
		log.info("################## 로그인횟수 : "+String.valueOf(count));
		
		if(count == null) {
			count = 0;
		}
		count++;
		req.getServletContext().setAttribute("accessCounter", count);
		
	}
	

	
	//로그아웃처리
	@GetMapping("logout")
	public String logout( HttpServletRequest request ) {
		
		HttpSession session = request.getSession(false);
		
		//세션만료
		if (session != null) {
            session.invalidate();
        }
		
		return "/login/loginHome";
	}
	
}
