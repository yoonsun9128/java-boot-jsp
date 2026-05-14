# OneToMany의 이용 단점?!

```java
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
```

`lazy`로 설정을 했어도 JPA가 `List`를 컬렉션 객체로 관리하고 있기 때문에
`answers.add(answer)`가 진행될 때 전체 `answer`의 로딩이 이뤄진다.

현재는 테스트로 인해서 데이터의 양이 적으니 문제가 되지 않으나,
나중에 데이터가 많을 때는 결국 메모리 낭비를 하게 된다.
