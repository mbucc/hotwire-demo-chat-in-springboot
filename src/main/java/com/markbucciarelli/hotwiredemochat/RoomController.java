package com.markbucciarelli.hotwiredemochat;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.Clock;
import java.util.Optional;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomController {

  private final RoomRepository roomRepository;
  private final MessageRepository messageRepository;
  private final Clock clock;

  public RoomController(
      RoomRepository roomRepository,
      MessageRepository messageRepository,
      Clock clock) {
    this.roomRepository = roomRepository;
    this.messageRepository = messageRepository;
    this.clock = clock;
  }

  @GetMapping("/rooms")
  public ModelAndView handleGet() {
    ModelAndView y = new ModelAndView("rooms");
    y.addObject("rooms", roomRepository.findAll());
    return y;
  }


  @GetMapping("/rooms/{roomId}")
  public ModelAndView handleRoomGet(@PathVariable("roomId") Long roomId) {
    ModelAndView y = new ModelAndView("room");
    Optional<Room> maybeRoom = roomRepository.findById(roomId);
    if (maybeRoom.isPresent()) {
      y.addObject("room", maybeRoom.get());
      y.addObject("messages", messageRepository.findByRoomId(roomId));
    } else {
      throw new ResponseStatusException(NOT_FOUND, "Unable to find room ID " + roomId);
    }
    return y;
  }

  @GetMapping("/rooms/new")
  public ModelAndView handleGetNew() {
    return new ModelAndView("room.new");
  }

  @PostMapping(
      path = "/rooms/new",
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ModelAndView handlePost(RoomFormData formData) {
    long now = this.clock.millis();
    roomRepository.save(
        new Room(
            null,
            formData.getName(),
            now,
            now));
    return new ModelAndView("room.new", HttpStatus.CREATED);
  }

  @Data
  static
  class RoomFormData {
    private String name;
  }

}
