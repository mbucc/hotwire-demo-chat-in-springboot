package com.markbucciarelli.hotwiredemochat;

import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_DATA1;
import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_DATA2;
import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_SCHEMA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@AutoConfigureMockMvc
class TestMessageRepositoryWithSpringContext {

  @Autowired
  MessageRepository messageRepository;


  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1})
  void testReadMessagesData() {

    List<Message> ys = this.messageRepository.findAll();
    assertNotNull(ys);
    assertEquals(1, ys.size());

    Message y = ys.get(0);
    assertNotNull(y);
    assertEquals(1, y.getId());
    assertEquals(1, y.getRoomId());
    assertEquals("first post", y.getContent());
    assertEquals(1608822796456L, y.getCreatedAt());
    assertEquals(1608822796456L, y.getUpdatedAt());

  }

  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1, TEST_DATA2})
  void testReadMessagesForRoom() {

    long now = 1608822796456L;
    BiFunction<Long, String, Message> f = (id, content) ->
        new Message(id, 1L, content, now, now);
    Message exp1 = f.apply(1L, "first post");
    Message exp2 = f.apply(3L, "second post in room one");

    List<Message> ys = this.messageRepository.findByRoomId(1L);
    assertNotNull(ys);
    assertEquals(2, ys.size());
    assertEquals(exp1, ys.get(0));
    assertEquals(exp2, ys.get(1));

  }

}



