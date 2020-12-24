package com.markbucciarelli.hotwiredemochat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.markbucciarelli.hotwiredemochat.MessageController.FormData;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestMessageController {

  private static final long clockTime = 1608840225000L;

  private MessageController messageController;
  private MessageRepository messageRepository;

  @BeforeEach
  void setup() {
    this.messageRepository = mock(MessageRepository.class);
    Clock clock = Clock.fixed(
        Instant.ofEpochMilli(clockTime),
        ZoneId.of("US/Eastern"));
    this.messageController = new MessageController(
        this.messageRepository,
        clock
    );
  }

  @Test
  void createRoomControllerInstance() {
    assertNotNull(this.messageController);
  }

  @Test
  void testFormPostSavesNewMessage() {

    String msg = "a test message";
    Long roomId = 123L;

    Message expected = new Message(null, 123L, msg, clockTime, clockTime);

    FormData formData = new FormData();
    formData.setContent(msg);

    this.messageController.handlePost(roomId, formData);

    verify(this.messageRepository).save(expected);

  }

}



