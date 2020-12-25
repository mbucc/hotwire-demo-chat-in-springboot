package com.markbucciarelli.hotwiredemochat;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TestRoom {

  // December 24, 2020 10:13:16.123 AM GMT-05:00
  final static long EPOCH_MILLIS = 1608822796123L;

  @Test
  void testGetCreatedAtDateConvertsToUsEastern() {
    Room r = new Room(1L, "one", EPOCH_MILLIS, EPOCH_MILLIS);
    assertEquals("12/24@10:13:16", r.getCreatedAtAsString());
  }


}
