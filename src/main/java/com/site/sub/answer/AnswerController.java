package com.site.sub.answer;

import com.site.sub.question.Question;
import com.site.sub.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
	private final QuestionService questionService;
	private final AnswerService answerService;

	@PostMapping("/create/{id}")
	@Transactional
	public String createAnswer(
			@PathVariable Integer id,
			@RequestParam("content") String content
	) {
		Question question = questionService.getQuestion(id);
		question.addAnswer(content);
		return String.format("redirect:/question/detail/%s", id);
	}

	@PostMapping("/create_2/{id}")
	@Transactional
	public String createAnswerManyToOne(
			@PathVariable Integer id,
			@RequestParam("content") String content
	) {
		Question question = questionService.getQuestion(id);
		answerService.create(question, content);
		return String.format("redirect:/question/detail/%s", id);
	}
}
