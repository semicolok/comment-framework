package com.semicolok.chat.controller;

import com.semicolok.chat.domain.calc.CalcInput;
import com.semicolok.chat.domain.calc.Result;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@MessageMapping("/add")
	@SendTo("/topic/showResult")
	public Result addNum(CalcInput input) throws Exception {
		Thread.sleep(2000);
		Result result = new Result(input.getNum1()+"+"+input.getNum2()+"="+(input.getNum1()+input.getNum2()));
		return result;
	}
}
