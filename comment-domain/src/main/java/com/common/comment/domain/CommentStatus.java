package com.common.comment.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommentStatus {
	CREATED("created"),
	DELETED("deleted");

	private String id;

	@JsonValue
	public String getId() {
		return id;
	}
}
