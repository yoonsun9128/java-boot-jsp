package com.site.sub.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class QuestionController {
	private final QuestioniService questioniService;

	@GetMapping("/question/list")
	@ResponseBody
	public String list() {
		List<Question> questions = questioniService.getList();
		String questionLiHtml = questions
				.stream()
				.map(q -> "<li>%d / %s</li>".formatted(q.getId(), q.getSubject()))
				.collect(Collectors.joining("\n\t\t"));

		return """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                    <meta charset="UTF-8">
                    <title>질문 목록</title>
                </head>
                <body>
                    <h1>질문 목록</h1>
                
                    <ul>
                        %s
                    </ul>
                </body>
                </html>
                """.formatted(questionLiHtml);
	}

	@GetMapping("/question/htmllist")
	public String htmlList(Model model) {
		//위 에있는 필드를 사용했다라는 의미 생략이 가능하다.
		// 컨트롤러 -> 서비스 -> 리포지터리 순서로 접근
		List<Question> questionList = this.questioniService.getList();
		model.addAttribute("questionList" , questionList);
		return "question_list";
	}

	@GetMapping(value = "/question/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Question question = questioniService.getQuestioin(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
}
