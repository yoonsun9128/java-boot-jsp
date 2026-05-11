package com.site.sub;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

	@OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Answer> answers;
	
	public Answer addAnswer(String content) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setQuestion(this);
		answer.setCreateDate(LocalDateTime.now());
		answers.add(answer);

		return answer;
	}
}
