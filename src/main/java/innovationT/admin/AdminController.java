package innovationT.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Controller

@Log4j2
public class AdminController {
	
	
	@GetMapping
	public String adminHome() {
		
		log.info("관리자페이지로 이동!");
		
		return "admin/adminHome";
	}

}
