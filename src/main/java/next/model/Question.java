package next.model;

import java.util.Date;

import javax.validation.constraints.Size;

public class Question {
	private long questionId;

	@Size(min = 4, max = 12)
	private String writer;

	@Size(min = 4, max = 200)
	private String title;

	private String contents;

	private Date createdDate;

	private int countOfComment;
	
	private boolean deleted = false;

	public Question() {
	}

	public Question(String writer, String title, String contents) {
		this(0, writer, title, contents, new Date(), 0, false);
	}

	public Question(long questionId, String writer, String title, String contents, Date createdDate,
			int countOfComment, boolean deleted) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.countOfComment = countOfComment;
		this.deleted = deleted;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getWriter() {
		return writer;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public long getTimeFromCreateDate() {
		return this.createdDate.getTime();
	}

	public int getCountOfComment() {
		return countOfComment;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public Question newQuestion(User user) {
		return new Question(user.getUserId(), title, contents);
	}
	
	public boolean isSameUser(User user) {
		return user.isSameUser(this.writer);
	}

	public void update(Question newQuestion) {
		this.title = newQuestion.title;
		this.contents = newQuestion.contents;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", writer=" + writer + ", title=" + title + ", contents="
				+ contents + ", createdDate=" + createdDate + ", countOfComment=" + countOfComment + ", deleted="
				+ deleted + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (questionId ^ (questionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (questionId != other.questionId)
			return false;
		return true;
	}
}
