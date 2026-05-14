package com.site.sub.answer;

import com.site.sub.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {
	private final AnswerRepository answerRepository;

	public void create(Question question, String content) {
		Answer newAnswer = new Answer();
		newAnswer.setQuestion(question);
		newAnswer.setContent(content);
		answerRepository.save(newAnswer);
	}
}
