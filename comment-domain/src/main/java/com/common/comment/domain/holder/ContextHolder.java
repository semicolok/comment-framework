package com.common.comment.domain.holder;

public class ContextHolder {
	private static final ThreadLocal<CommentServiceType> contextHolder = new ThreadLocal<CommentServiceType>();

	public static void setCommentServiceType(CommentServiceType commentServiceType) {
		contextHolder.set(commentServiceType);
	}

	public static CommentServiceType getCommentServiceType() {
		return contextHolder.get();
	}

	public static void clearCommentServiceTyp() {
		contextHolder.remove();
	}
}
