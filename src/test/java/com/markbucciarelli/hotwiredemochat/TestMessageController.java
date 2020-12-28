package com.markbucciarelli.hotwiredemochat;

import static com.markbucciarelli.hotwiredemochat.MessageController.TURBO_STREAM_MIME;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.markbucciarelli.hotwiredemochat.MessageController.FormData;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.ServletWebRequest;


class TestMessageController {

  private static final long clockTime = 1608840225000L;

  private MessageController messageController;
  private MessageRepository messageRepository;
  private ServletWebRequest servletWebRequest;
  private HttpServletRequest httpServletRequest;
  private HttpServletResponse httpServletResponse;

  @BeforeEach
  void setup() {

    this.messageRepository = mock(MessageRepository.class);

    this.httpServletRequest = mock(HttpServletRequest.class);
    this.httpServletResponse = mock(HttpServletResponse.class);
    servletWebRequest = new ServletWebRequest(httpServletRequest, httpServletResponse);

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
  void testFormTurboStreamPostSavesNewMessage() {
    verifyMessageSaveCalled(TURBO_STREAM_MIME);
  }

  @Test
  void testFormHTMLPostSavesNewMessage() {
    verifyMessageSaveCalled("text/html");
  }

  private void verifyMessageSaveCalled(String acceptHeader) {
    String msg = "a test message";
    Long roomId = 123L;

    FormData formData = new FormData();
    formData.setContent(msg);

    when(httpServletRequest.getHeader("Accept")).thenReturn(acceptHeader);

    this.messageController.handlePost(roomId, formData, servletWebRequest);

    Message expected = new Message(null, 123L, msg, clockTime, clockTime);
    verify(this.messageRepository).save(expected);
  }

}



