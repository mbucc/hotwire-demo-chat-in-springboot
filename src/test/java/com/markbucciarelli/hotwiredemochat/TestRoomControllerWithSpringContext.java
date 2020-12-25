package com.markbucciarelli.hotwiredemochat;

import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_DATA1;
import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_SCHEMA;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
class TestRoomControllerWithSpringContext {


  @Autowired
  private MockMvc mockMvc;


  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1})
  void testRoomsGet() throws Exception {
    this.mockMvc
        .perform(get("/rooms"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("New room")))
        .andExpect(content().string(containsString("href=\"/rooms/1\">one</a>")));
  }

  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1})
  void testRoomOneGet() throws Exception {
    this.mockMvc
        .perform(get("/rooms/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("<h1>Room one</h1>")))
        .andExpect(content().string(containsString("<p id=\"1\">")));
  }

  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1})
  void testRoomGetForm() throws Exception {
    this.mockMvc
        .perform(get("/rooms/new"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("New Room")))
        .andExpect(content().string(containsStringIgnoringCase("action=\"/rooms/new\"")))
        .andExpect(content().string(containsStringIgnoringCase("method=\"post\"")));
  }

  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1})
  void testFormPostReturnsCreated() throws Exception {
    this.mockMvc
        .perform(
            post("/rooms/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "a second room"))
        .andExpect(status().isCreated());
  }


}



