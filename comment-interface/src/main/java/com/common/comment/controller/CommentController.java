package com.common.comment.controller;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CommentController {

	@RequestMapping(value = {"/comments"}, method = RequestMethod.GET)
	public ResponseEntity<Object> getComments() {
		Map<String, String> map = Maps.newHashMap();

		map.put("data", "success");
		return new ResponseEntity(map, HttpStatus.OK);
	}
}
