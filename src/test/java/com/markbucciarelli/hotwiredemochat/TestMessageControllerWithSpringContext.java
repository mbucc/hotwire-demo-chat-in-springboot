package com.markbucciarelli.hotwiredemochat;

import static com.markbucciarelli.hotwiredemochat.MessageController.TURBO_STREAM_MIME;
import static com.markbucciarelli.hotwiredemochat.MessageController.TURBO_STREAM_MIME_WITH_ENCODING;
import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_DATA1;
import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_SCHEMA;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
  void testFormPostFormEncoded() throws Exception {
    this.mockMvc
        .perform(
            post("/rooms/1/messages/new")
                .accept(TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("content", "a test message"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/rooms/1"));
  }


  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1})
  void testFormPostTurboStream() throws Exception {

    String message = "a test message";

    String expectedPattern =
        "(?s).*<turbo-stream action=\"append\".*target=\"messages\">"
            + ".*<template>"
            + ".*<p id=\"message-2\">"
            + ".*" + message
            + ".*</p>"
            + ".*</template>"
            + ".*</turbo-stream>"
            + ".*";

    this.mockMvc
        .perform(
            post("/rooms/1/messages/new")
                .accept(TURBO_STREAM_MIME)
                .contentType(MULTIPART_FORM_DATA)
                .param("content", message))
        .andExpect(status().isOk())
        .andExpect(header()
            .string(
                "Content-Type",
                TURBO_STREAM_MIME_WITH_ENCODING))
        .andExpect(content().string(matchesPattern(expectedPattern)));
  }

}



