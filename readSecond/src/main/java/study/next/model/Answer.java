package study.next.model;

import java.util.Date;

public class Answer {
	private long answerId;
	private String writer;
	private String contents;
	private Date createdDate;
	private long questionId;
	
	public Answer(String writer, String contents, long questionId){
		this(0, writer, contents, new Date(), questionId);
	}
	public Answer(long answerId, String writer, String contents, Date createDate, long questionId){
		this.answerId = answerId;
		this.writer = writer;
		this.contents = contents;
		this.createdDate = createDate;
		this.questionId = questionId;
	}
	
	public long getAnswerId() {
		return answerId;
	}
	public String getWriter() {
		return writer;
	}
	public String getContents() {
		return contents;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public long getQuestionId() {
		return questionId;
	}
}
