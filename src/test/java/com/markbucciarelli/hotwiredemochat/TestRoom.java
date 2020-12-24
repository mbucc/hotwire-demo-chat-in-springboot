package com.markbucciarelli.hotwiredemochat;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class TestRoom {

  // 2020-12-24 10:13:16.123
  final static long EPOCH_MILLIS = 1608822796123L;
  final static ZoneId EASTERN = ZoneId.of("US/Eastern");

  @Test
  void testGetCreatedAtDateConvertsToUsEastern() {
    Room r = new Room(1L, "one", EPOCH_MILLIS, EPOCH_MILLIS);
    ZonedDateTime expected = Instant.ofEpochMilli(EPOCH_MILLIS).atZone(EASTERN);
    assertEquals(expected, r.getCreatedAtAsDate(EASTERN));
  }


}
