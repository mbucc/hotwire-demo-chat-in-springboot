package com.markbucciarelli.hotwiredemochat;

import java.time.Clock;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rooms/{roomId}/messages/new")
public class MessageController {

  private final MessageRepository messageRepository;
  private final Clock clock;

  public MessageController(
      MessageRepository messageRepository,
      Clock clock) {
    this.messageRepository = messageRepository;
    this.clock = clock;
  }

  @GetMapping
  public ModelAndView handleGet(@PathVariable("roomId") Long roomId) {
    ModelAndView y = new ModelAndView("messages.new");
    y.addObject("roomId", roomId);
    return y;
  }

  @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ModelAndView handlePost(
      @PathVariable("roomId") Long roomId,
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
    return new ModelAndView("messages.new", HttpStatus.CREATED);
  }

  @Data
  static
  class FormData {
    private String content;
  }

}
