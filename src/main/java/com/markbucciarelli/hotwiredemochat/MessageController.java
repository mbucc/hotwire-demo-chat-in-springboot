package com.markbucciarelli.hotwiredemochat;

import static com.markbucciarelli.hotwiredemochat.RoomController.ROOM_DETAIL_URL;
import static com.markbucciarelli.hotwiredemochat.RoomController.ROOM_ID_PATH_PARAM;

import java.time.Clock;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController {

  public static final String MESSAGE_NEW_URL = ROOM_DETAIL_URL + "/messages/new";

  public static final String MESSAGE_NEW_VIEW = "message.new";

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
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ModelAndView handlePost(
      @PathVariable(ROOM_ID_PATH_PARAM) Long roomId,
      FormData formData
  ) {
    long now = this.clock.millis();
    messageRepository.save(
        new Message(
            null,
            roomId,
            formData.getContent(),
            now,
            now));
    return new ModelAndView(MESSAGE_NEW_VIEW, HttpStatus.CREATED);
  }

  @Data
  static
  class FormData {
    private String content;
  }

}
