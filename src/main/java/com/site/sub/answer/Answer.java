package com.site.sub.answer;

import com.site.sub.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private LocalDateTime createDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Question question;

	@Column(columnDefinition = "TEXT")
	private String content;
}
