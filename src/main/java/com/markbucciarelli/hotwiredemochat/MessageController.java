package com.markbucciarelli.hotwiredemochat;

import java.time.Clock;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/messages/new")
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
  public ModelAndView handleGet() {
    return new ModelAndView("messages.new");
  }

  @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ModelAndView handlePost(FormData formData) {
    long now = this.clock.millis();
    messageRepository.save(
        new Message(
            null,
            formData.getRoomId(),
            formData.getContent(),
            now,
            now));
    return new ModelAndView("messages.new", HttpStatus.CREATED);
  }

  @Data
  static
  class FormData {
    private String content;
    private Long roomId;
  }

}
