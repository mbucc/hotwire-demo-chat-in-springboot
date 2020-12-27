package com.markbucciarelli.hotwiredemochat;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.markbucciarelli.hotwiredemochat.RoomController.RoomFormData;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestRoomController {

  private static final long clockTime = 1608840225000L;

  private RoomController roomController;
  private RoomRepository roomRepository;
  private MessageRepository messageRepository;

  @BeforeEach
  void setup() {

    this.roomRepository = mock(RoomRepository.class);
    this.messageRepository = mock(MessageRepository.class);

    Clock clock = Clock.fixed(
        Instant.ofEpochMilli(clockTime),
        ZoneId.of("US/Eastern"));

    this.roomController = new RoomController(
        this.roomRepository,
        this.messageRepository,
        clock
    );

  }

  @Test
  void createRoomControllerInstance() {
    assertNotNull(this.roomController);
  }


  @Test
  void testPostNew() {

    RoomFormData formData = new RoomFormData();
    formData.setName("test room name");

    Room expected = new Room(null, formData.getName(), clockTime, clockTime);

    this.roomController.handlePostNew(formData);

    verify(this.roomRepository).save(expected);

  }

  @Test
  void testPostEdit() {

    RoomFormData formData = new RoomFormData();
    formData.setName("new room name");

    long insertTime = clockTime - 100000;
    Room roomOnFile =
        new Room(1L, "old name", insertTime, insertTime);
    when(this.roomRepository.findById(1L)).thenReturn(Optional.of((roomOnFile)));

    Room expectedNewRoom =
        new Room(1L, formData.getName(), insertTime, clockTime);


    this.roomController.handlePostEdit(1L, formData);

    verify(this.roomRepository).save(expectedNewRoom);

  }

}



