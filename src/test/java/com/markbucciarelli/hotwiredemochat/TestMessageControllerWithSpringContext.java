package com.markbucciarelli.hotwiredemochat;

import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_DATA1;
import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_SCHEMA;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TestMessageControllerWithSpringContext {


  @Autowired
  private MockMvc mockMvc;


  @Test
  void testFormGet() throws Exception {
    String url = "/rooms/1/messages/new";
    this.mockMvc
        .perform(get(url))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("New Message")))
        .andExpect(content().string(containsString("Content:")))
        .andExpect(content().string(containsString("Send")))
        .andExpect(content().string(containsString("Back")))
        .andExpect(content().string(containsStringIgnoringCase("action=\"" + url + "\"")))
        .andExpect(content().string(containsStringIgnoringCase("method=\"post\"")));
  }

  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1})
  void testFormPostReturnsCreated() throws Exception {
    this.mockMvc
        .perform(
            post("/rooms/1/messages/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("content", "a test message"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/rooms/1"));
  }
}



