package com.site.sub.question;

import com.site.sub.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //생성자 주입
public class QuestioniService {
	private final QuestionRepository questionRepository;

	public List<Question> getList() {
		return questionRepository.findAll();
	}

	public Question getQuestioin(int id) {
		Optional<Question> opQuestion = questionRepository.findById(id);

		if (opQuestion.isPresent()) {
			return opQuestion.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
}
