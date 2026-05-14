package com.site.sub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	//url과 매핑된 메서드는 결과값을 리턴해야한다.
	@GetMapping("/")
	public String index() {
		return "redirect:/question/htmllist";
	}
}
