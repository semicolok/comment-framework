package com.common.comment.domain.holder;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CommentServiceTypeHolder {
	private CommentServiceType serviceType;

	public void setServiceType(String commentServiceTypeId) {
		this.serviceType = CommentServiceType.findById(commentServiceTypeId);
	}
}
