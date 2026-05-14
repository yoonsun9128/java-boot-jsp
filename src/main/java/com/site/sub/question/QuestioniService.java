package com.site.sub.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //생성자 주입
public class QuestioniService {
	private final QuestionRepository questionRepository;

	public List<Question> getList() {
		return questionRepository.findAll();
	}
}
