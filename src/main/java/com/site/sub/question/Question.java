package com.site.sub.question;

import com.site.sub.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private LocalDateTime createDate;

	@Column(length = 200)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String content;

	// eager 보다는 lazy가 좋다. lazy는 필요할때마다 렌더링 하는 방식으로 eager보다 메모리를 낭비를 줄일 수 있다.
//	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	@OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Answer> answers = new ArrayList<>();

	public Answer addAnswer(String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setQuestion(this);
		answer.setCreateDate(LocalDateTime.now());
		answers.add(answer);

		return answer;
	}
}
