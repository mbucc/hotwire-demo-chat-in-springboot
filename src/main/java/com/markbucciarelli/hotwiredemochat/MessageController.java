package com.markbucciarelli.hotwiredemochat;

import static com.markbucciarelli.hotwiredemochat.RoomController.ROOM_DETAIL_URL;
import static com.markbucciarelli.hotwiredemochat.RoomController.ROOM_ID_PATH_PARAM;
import static com.markbucciarelli.hotwiredemochat.RoomController.redirectToDetail;

import java.time.Clock;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController {

  public static final String MESSAGE_NEW_URL = ROOM_DETAIL_URL + "/messages/new";

  public static final String MESSAGE_NEW_VIEW = "message.new";
  public static final String MESSAGE_STREAM_VIEW = "message.turbo-stream";


  public static final String TURBO_STREAM_MIME =
      "text/html; turbo-stream";
  public static final String TURBO_STREAM_MIME_WITH_ENCODING =
      "text/html; turbo-stream; charset=utf-8";

  private final MessageRepository messageRepository;
  private final Clock clock;

  public MessageController(
      MessageRepository messageRepository,
      Clock clock) {
    this.messageRepository = messageRepository;
    this.clock = clock;
  }

  @GetMapping(MESSAGE_NEW_URL)
  public ModelAndView handleGet(@PathVariable(ROOM_ID_PATH_PARAM) Long roomId) {
    ModelAndView y = new ModelAndView(MESSAGE_NEW_VIEW);
    y.addObject("roomId", roomId);
    return y;
  }

  @PostMapping(
      path = MESSAGE_NEW_URL,
      consumes = {
          MediaType.APPLICATION_FORM_URLENCODED_VALUE,
          // When the new message form was put in a frame on the
          // room detail, clicking the send button posted set the
          // content type to multipart form data.
          MediaType.MULTIPART_FORM_DATA_VALUE})
  public ModelAndView handlePost(
      @PathVariable(ROOM_ID_PATH_PARAM) Long roomId,
      FormData formData,
      ServletWebRequest swr
  ) {
    // Spring is not able to distinguish between the accept headers "text/html"
    // and "text/html; turbo-stream" with the produces attribute in the
    // PostMapping annotation.  So do this manually.
    if (swr.getRequest().getHeader("Accept").startsWith(TURBO_STREAM_MIME)) {
      return handlePostTurboStream(roomId, formData, swr);
    }
    long now = this.clock.millis();
    messageRepository.save(
        new Message(
            null,
            roomId,
            formData.getContent(),
            now,
            now));
    return new ModelAndView(redirectToDetail(roomId));
  }

  public ModelAndView handlePostTurboStream(
      Long roomId,
      FormData formData,
      ServletWebRequest swr
  ) {
    long now = this.clock.millis();
    Message m = messageRepository.save(
        new Message(
            null,
            roomId,
            formData.getContent(),
            now,
            now));
    swr.getResponse().setContentType(TURBO_STREAM_MIME_WITH_ENCODING);
    ModelAndView y = new ModelAndView(MESSAGE_STREAM_VIEW);
    y.addObject("message", m);
    return y;
  }

  @Data
  static
  class FormData {
    private String content;
  }

}
