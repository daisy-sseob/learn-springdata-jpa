package me.sseob.learnspringdatajpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Comment {

	@Id @GeneratedValue
	private Long id;

	private String comment;

	@ManyToOne
	private Post post;

	private Integer likeCount = 0;

	private LocalDateTime created;

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer like) {
		this.likeCount = like;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", comment='" + comment + '\'' +
				", created=" + created +
				", likeCount=" + likeCount +
				'}';
	}
}
