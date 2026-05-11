package com.site.sub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	//url과 매핑된 메서드는 결과값을 리턴해야한다.
	@GetMapping("/sbb")
	@ResponseBody //URL 요청에 대한 응답으로 문자열을 리턴한다는
	public String index() {
		return "안녕하세요 sbb에 오신 것을 환영합니다.";
	}

}
