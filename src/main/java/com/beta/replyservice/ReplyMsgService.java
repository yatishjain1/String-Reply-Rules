package com.beta.replyservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class ReplyMsgService {
	public final static String INVALID_INPUT = "Invalid input";

	private enum RuleType {
		REVERSE(1), ENCODING(2);

		private int value;
		private static Map<Object, Object> map = new HashMap<>();

		private RuleType(int value) {
			this.value = value;
		}

		static {
			for (RuleType ruleType : RuleType.values()) {
				map.put(ruleType.value, ruleType);
			}
		}

		public static RuleType valueOf(int ruleType) {
			return (RuleType) map.get(ruleType);
		}

	}
	
	public String encodeMD5(String outputMesssage) {
		return DigestUtils.md5Hex(outputMesssage);
	}

	public String reverseString(String outputMesssage) {
		return new StringBuilder(outputMesssage).reverse().toString();
	}

	public String convertWithRule(String message) {
		String[] arrOfMsg = message.split("-");
		String rules = arrOfMsg[0];
		if (arrOfMsg.length != 2 || !isInteger(rules) || rules.length() != RuleType.values().length) {
			return INVALID_INPUT;
		}
		String outputMesssage = arrOfMsg[1];
		for (int i = 0; i < rules.length(); ++i) {
			int digit = Integer.parseInt(rules.charAt(i) + "");
			if (RuleType.valueOf(digit) == null) {
				return INVALID_INPUT;
			} else {
				outputMesssage = evaluateRule(RuleType.valueOf(digit), outputMesssage);
			}
		}

		return outputMesssage;
	}

	private String evaluateRule(RuleType ruleType, String outputMesssage) {
		switch (ruleType) {
		case REVERSE:
			outputMesssage = reverseString(outputMesssage);
			break;
		case ENCODING:
			outputMesssage = encodeMD5(outputMesssage);
			break;
		default:
			outputMesssage = INVALID_INPUT;
			break;
		}
		return outputMesssage;
	}


	private boolean isInteger(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
