package com.site.sub;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class QuestRepositoryTest {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

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

	@Test
	@DisplayName("답변 생성")
	@Transactional
	void v8() {
		Question question = questionRepository.findById(2).get();

		Answer answer = new Answer();
		answer.setContent("네 자동으로 생성됩니다.");
		answer.setQuestion(question);
		answer.setCreateDate(LocalDateTime.now());
		answerRepository.save(answer);
	}

	@Test
	@DisplayName("onetomany 답변생성")
	@Transactional
	void v9() {
		Question question = questionRepository.findById(2).get();

		int beforeCount = question.getAnswers().size();

		// 아래 코드로 객체는 생성되어서 answers에 추가되지만 실제 INSERT는 트랜잭션이 끝나면 수행됩니다.
		// 만약에 롤백이 된다면 INSERT 되지 않습니다.
		// 그런데 이 메서드(t9) 특성상 롤백이 됩니다.
		// 그래서 밑에 `questionRepository.flush();` 추가 했습니다.
		Answer newAnswer = question.addAnswer("네 자동으로 생성됩니다.");

		// 트랜잭션이 종료된 이후에 DB에 반영되기 때문에 현재는 일단 0으로 설정된다.
		assertThat(newAnswer.getId()).isEqualTo(0);

		int afterCount = question.getAnswers().size();

		assertThat(afterCount).isEqualTo(beforeCount + 1);

		questionRepository.flush(); // 추가된 코드
	}
}
