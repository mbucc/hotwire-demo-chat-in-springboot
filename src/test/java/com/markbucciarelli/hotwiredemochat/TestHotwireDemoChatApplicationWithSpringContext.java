package com.markbucciarelli.hotwiredemochat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class TestHotwireDemoChatApplicationWithSpringContext {

  final static String TEST_SCHEMA = "/test-schema-h2.sql";
  final static String TEST_DATA = "/test-data-h2.sql";

  @Autowired
  RoomRepository roomRepository;

  @Autowired
  MessageRepository messageRepository;

  @Test
  @SuppressWarnings("java:S2699")
    // Tell SonarLint there is no need for an assert.
  void contextLoads() {
  }

  @Test
  @Sql({TEST_SCHEMA})
  @SuppressWarnings("java:S2699")
    // Tell SonarLint there is no need for an assert.
  void testH2Schema() {
  }

  @Test
  @Sql({TEST_SCHEMA, TEST_DATA})
  void testReadRoomData() {

    Iterable<Room> ys = this.roomRepository.findAll();
    assertNotNull(ys);
    Iterator<Room> i = ys.iterator();
    assertTrue(i.hasNext());

    Room y = i.next();
    assertNotNull(y);
    assertEquals(1, y.getId());
    assertEquals("one", y.getName());
    assertEquals(1608822796123L, y.getCreatedAt());
    assertEquals(1608822796123L, y.getUpdatedAt());

    assertFalse(i.hasNext());

  }

  @Test
  @Sql({TEST_SCHEMA, TEST_DATA})
  void testReadMessagesData() {

    Iterable<Message> ys = this.messageRepository.findAll();
    assertNotNull(ys);
    Iterator<Message> i = ys.iterator();
    assertTrue(i.hasNext());

    Message y = i.next();
    assertNotNull(y);
    assertEquals(1, y.getId());
    assertEquals(1, y.getRoomId());
    assertEquals("first post", y.getContent());
    assertEquals(1608822796456L, y.getCreatedAt());
    assertEquals(1608822796456L, y.getUpdatedAt());

    assertFalse(i.hasNext());

  }


}
