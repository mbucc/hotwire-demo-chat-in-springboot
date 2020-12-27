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

  public static final String ROOM_LIST_URL = "/rooms";
  public static final String ROOM_NEW_URL = "/rooms/new";
  public static final String ROOM_DETAIL_URL = "/rooms/{roomId}";
  public static final String ROOM_EDIT_URL = "/rooms/{roomId}/edit";

  public static final String ROOM_ID_PATH_PARAM = "roomId";

  public static final String ROOM_LIST_VIEW = "rooms";
  public static final String ROOM_DETAIL_VIEW = "room";
  public static final String ROOM_NEW_VIEW = "room.new";
  public static final String ROOM_EDIT_VIEW = "room.edit";


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

  //----------------------------------------------------------------------------
  //
  //                          H A N D L E R S
  //
  //----------------------------------------------------------------------------

  static String redirectToDetail(Long roomId) {
    String s0 = "redirect:" + ROOM_DETAIL_URL;
    String needle = "{" + ROOM_ID_PATH_PARAM + "}";
    return s0.replace(needle, "" + roomId);
  }

  @GetMapping(ROOM_LIST_URL)
  public ModelAndView handleGet() {
    ModelAndView y = new ModelAndView(ROOM_LIST_VIEW);
    y.addObject("rooms", roomRepository.findAll());
    return y;
  }

  @GetMapping(ROOM_DETAIL_URL)
  public ModelAndView handleRoomGet(@PathVariable(ROOM_ID_PATH_PARAM) Long roomId) {
    return renderRoomView(roomId, ROOM_DETAIL_VIEW);
  }

  @GetMapping(ROOM_NEW_URL)
  public ModelAndView handleGetNew() {
    return new ModelAndView(ROOM_NEW_VIEW);
  }

  @PostMapping(
      path = ROOM_NEW_URL,
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public ModelAndView handlePostNew(RoomFormData formData) {
    long now = this.clock.millis();
    roomRepository.save(
        new Room(
            null,
            formData.getName(),
            now,
            now));
    return new ModelAndView(ROOM_NEW_VIEW, HttpStatus.CREATED);
  }

  @GetMapping(ROOM_EDIT_URL)
  public ModelAndView handleGetEdit(@PathVariable(ROOM_ID_PATH_PARAM) Long roomId) {
    return renderRoomView(roomId, ROOM_EDIT_VIEW);
  }

  //----------------------------------------------------------------------------
  //
  //                          U T I L I T Y
  //
  //----------------------------------------------------------------------------

  @PostMapping(ROOM_EDIT_URL)
  public ModelAndView handlePostEdit(
      @PathVariable(ROOM_ID_PATH_PARAM) Long roomId,
      RoomFormData formData
  ) {
    Room oldRoom = getRoomOrDie(roomId);
    Room newRoom = new Room(
        oldRoom.getId(),
        formData.getName(),
        oldRoom.getCreatedAt(),
        this.clock.millis());
    this.roomRepository.save(newRoom);
    return new ModelAndView(redirectToDetail(roomId));
  }

  private ModelAndView renderRoomView(Long roomId, String viewName) {
    ModelAndView y = new ModelAndView(viewName);
    y.addObject("room", getRoomOrDie(roomId));
    y.addObject("messages", messageRepository.findByRoomId(roomId));
    return y;
  }

  private Room getRoomOrDie(Long roomId) {
    Optional<Room> maybeRoom = roomRepository.findById(roomId);
    if (maybeRoom.isPresent()) {
      return maybeRoom.get();
    } else {
      throw new ResponseStatusException(NOT_FOUND, "Unable to find room ID " + roomId);
    }

  }

  @Data
  static
  class RoomFormData {
    private String name;
  }

}
