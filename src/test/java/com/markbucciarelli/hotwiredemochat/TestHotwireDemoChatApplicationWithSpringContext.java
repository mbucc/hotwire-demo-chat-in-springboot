package com.markbucciarelli.hotwiredemochat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class TestHotwireDemoChatApplicationWithSpringContext {

  final static String TEST_SCHEMA = "/test-schema-h2.sql";
  final static String TEST_DATA1 = "/test-data1-h2.sql";
  final static String TEST_DATA2 = "/test-data2-h2.sql";

  @Autowired
  RoomRepository roomRepository;

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


}
