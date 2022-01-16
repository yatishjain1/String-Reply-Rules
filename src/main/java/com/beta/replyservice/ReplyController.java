package com.beta.replyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReplyController {
	
	@Autowired
    private ReplyMsgService service;
	
	//sample method to test default url
	@GetMapping("/")
	public String hello() {
		return ("Hello world");
	}

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {
		return new ReplyMessage(message);
	}
	
	@GetMapping("v2/reply/{message}")
	public ResponseEntity<ReplyMessage> replyingWithRule(@PathVariable String message) {
		ReplyMessage replyMessage =new ReplyMessage(service.convertWithRule(message));
		if( replyMessage.getMessage().equals(ReplyMsgService.INVALID_INPUT)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(replyMessage);
		}
		return ResponseEntity.status(HttpStatus.OK).body(replyMessage);
	}
	
}