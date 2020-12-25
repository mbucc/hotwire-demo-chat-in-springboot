package com.markbucciarelli.hotwiredemochat;

import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_DATA1;
import static com.markbucciarelli.hotwiredemochat.TestHotwireDemoChatApplicationWithSpringContext.TEST_SCHEMA;
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
class TestRoomRepositoryWithSpringContext {

  @Autowired
  RoomRepository roomRepository;

  @Test
  @Sql({TEST_SCHEMA, TEST_DATA1})
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


}
