package com.common.comment.domain.holder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum CommentServiceType {
	PLAIN_BOARD("plainBoard"),
	CHAT_BOARD("chatBoard");

	private String id;

	public static CommentServiceType findById(String commentServiceTypeId) {
		for (CommentServiceType type : CommentServiceType.values()) {
			if (StringUtils.equals(type.getId(), commentServiceTypeId)) {
				return type;
			}
		}
		return PLAIN_BOARD;
	}
}
