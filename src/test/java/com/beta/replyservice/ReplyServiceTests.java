package com.beta.replyservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {ReplyController.class, ReplyMsgService.class})
@WebMvcTest
class ReplyServiceTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ReplyMsgService service;
	
	@Test
	void testApplication() throws Exception {

		 RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
					"/v2/reply/12-kbzw9ru").accept(
					MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();

			String expected = "{\"message\":\"5a8973b3b1fafaeaadf10e195c6e1dd4\"}";

			JSONAssert.assertEquals(expected, result.getResponse()
					.getContentAsString(), false);
    }
	
	@Test
    void tesReverse() {
        String msg = service.reverseString("kbzw9ru");
        assertEquals("ur9wzbk", msg);
    }
	
	@Test
    void tesencoding() {
        String msg = service.encodeMD5("kbzw9ru");
        assertEquals("0fafeaae780954464c1b29f765861fad", msg);
    }


	
	@Test
    void testDoubleReverse() {
        String msg = service.convertWithRule("11-kbzw9ru");
        assertEquals("kbzw9ru", msg);
    }
	
	@Test
    void testReverseAndEncode() {
        String msg = service.convertWithRule("12-kbzw9ru");
        assertEquals("5a8973b3b1fafaeaadf10e195c6e1dd4", msg);
    }
	
	@Test
    void testDoubleEncode() {
        String msg = service.convertWithRule("22-kbzw9ru");
        assertEquals("e8501e64cf0a9fa45e3c25aa9e77ffd5", msg);
    }
	
	@Test
    void testInvalid() {
        String msg = service.convertWithRule("32-kbzw9ru");
        assertEquals("Invalid input", msg);
    }
	
}
