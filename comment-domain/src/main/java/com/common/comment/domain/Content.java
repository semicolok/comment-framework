package com.common.comment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Table(name = "content")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Content {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "CONTENT_ID")
	private String id;

	@Column(length = 4096)
	private String text;

	@MapsId
	@OneToOne
	@JoinColumn(name = "COMMENT_ID")
	private Comment comment;

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
