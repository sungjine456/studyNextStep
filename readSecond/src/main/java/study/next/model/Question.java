package study.next.model;

import java.util.Date;

public class Question {
	private long questionId;
	private User writer;
	private String title;
	private String contents;
	private Date createdDate;
	private int countOfAnswer;
	
	public Question(User writer, String title, String contents){
		this(0, writer, title, contents, new Date(), 0);
	}
	public Question(long questionId, User writer, String title, String contents, Date createDate, int countOfAnswer){
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createDate;
		this.countOfAnswer = countOfAnswer;
	}
	
	public long getQuestionId() {
		return questionId;
	}
	public User getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public String getContents() {
		return contents;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public int getCountOfAnswer() {
		return countOfAnswer;
	}
	
	public void update(String title, String contents){
		this.title = title;
		this.contents = contents;
	}
}
