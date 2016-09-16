package com.common.comment.config.interceptor;

import com.common.comment.domain.holder.CommentServiceTypeHolder;
import com.common.comment.domain.holder.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommentServiceTypeInterceptor extends HandlerInterceptorAdapter {
	private static final String X_COMMENTSERVICE_TYPE = "x-commentservice-type";

	@Autowired
	private CommentServiceTypeHolder commentServiceTypeHolder;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		final String commentServiceType = request.getHeader(X_COMMENTSERVICE_TYPE);
		commentServiceTypeHolder.setServiceType(commentServiceType);
		ContextHolder.setCommentServiceType(commentServiceTypeHolder.getServiceType());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		ContextHolder.clearCommentServiceTyp();
		
		super.postHandle(request, response, handler, modelAndView);
	}
}
