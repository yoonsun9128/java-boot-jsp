package com.site.sub;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class QuestRepositoryTest {
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	@DisplayName("findall")
	void t1() {
		List<Question> questions = questionRepository.findAll();
		assertThat(questions).hasSize(2);

		Question question = questions.get(0);
		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요");
	}

	@Test
	@DisplayName("finById")
	void v2() {
		Question question = questionRepository.findById(1).get();
		// 값이 없으면 💥 NoSuchElementException 바로 터짐
//		// orElseThrow - 없으면 내가 원하는 예외 던지기
//		Question q = questionRepository.findById(1)
//				.orElseThrow(() -> new RuntimeException("없음!"));
		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요");
	}

	@Test
	@DisplayName("findBySubject")
	void t3() {
		Question question = questionRepository.findBySubject("sbb가 무엇인가요").get();
		// SELECT * FROM question WHERE subject = 'sbb가 무엇인가요?'
		assertThat(question.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("findBySubjectAndContent")
	void t4() {
		Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요", "sbb에 대해서 알고 싶습니다.").get();
		// SELECT * FROM question WHERE subject = 'sbb가 무엇인가요?' AND content = 'sbb에 대해서 알고 싶습니다.'
		assertThat(question.getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("findBySubjectLike")
	void t5() {
		/*
		* ssb% : ssb로 시작하는 문자열
		* %ssb : ssb로 끝나는 문자열
		* %ssb% : ssb를 포함한 문자열
		* */
		List<Question> questions = questionRepository.findBySubjectLike("sbb%");

		Question question = questions.get(0);
		assertThat(question.getSubject()).isEqualTo("sbb가 무엇인가요");
	}

	@Test
	@DisplayName("수정")
	@Transactional
	void t6() {
		Question question = questionRepository.findById(1).get();
		assertThat(question).isNotNull();

		question.setSubject("수정된 제목");
		questionRepository.save(question);

		Question foundQuestion = questionRepository.findBySubject("수정된 제목").get();
		assertThat(foundQuestion).isNotNull();
	}

	@Test
	@DisplayName("삭제")
	void t7() {
		assertThat(questionRepository.count()).isEqualTo(2);

		Question question = questionRepository.findById(1).get();
		questionRepository.delete(question);

		assertThat(questionRepository.count()).isEqualTo(1);
	}
}
