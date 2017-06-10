package study.next.model;

import java.util.Date;

public class Question {
	private long questionId;
	private String writer;
	private String title;
	private String contents;
	private Date createdDate;
	private int countOfAnswer;
	
	public Question(String writer, String title, String contents){
		this(0, writer, title, contents, new Date(), 0);
	}
	public Question(long questionId, String writer, String title, String contents, Date createDate, int countOfAnswer){
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
	public String getWriter() {
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
}
