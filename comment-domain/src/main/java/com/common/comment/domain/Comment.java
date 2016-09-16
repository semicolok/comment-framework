package com.common.comment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Table(name = "comment")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Comment {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "COMMENT_ID")
	private String id;

	@Enumerated(EnumType.STRING)
	private CommentStatus status = CommentStatus.CREATED;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTHOR_ID")
	private Author author;

	@OneToOne(mappedBy = "comment")
	private Content content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
		this.modifiedAt = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		this.modifiedAt = new Date();
	}
}
