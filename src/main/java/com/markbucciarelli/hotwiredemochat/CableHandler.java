package com.markbucciarelli.hotwiredemochat;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class CableHandler extends TextWebSocketHandler {

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) {
    System.out.println("session = " + session);
    System.out.println("message = " + message);
    throw new UnsupportedOperationException("not implemented");
  }


}
