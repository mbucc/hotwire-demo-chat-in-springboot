package com.markbucciarelli.hotwiredemochat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class CableHandler
    extends TextWebSocketHandler
    implements SubProtocolCapable
{

  private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) {
    System.out.println("session = " + session);
    System.out.println("message = " + message);
    throw new UnsupportedOperationException("not implemented");
  }

  @Override
  public List<String> getSubProtocols() {
    return Collections.singletonList("actioncable-v1-json");
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws IOException {
    log.info("Server connection opened");
    sessions.add(session);
    TextMessage message = new TextMessage("{\"type\":\"welcome\"}");
    log.info("Server sends: {}", message);
    session.sendMessage(message);
  }

}
