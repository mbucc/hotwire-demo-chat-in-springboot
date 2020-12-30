package com.markbucciarelli.hotwiredemochat;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class ConfigurationBeans implements WebSocketConfigurer {

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
        .addHandler(myHandler(), "/cable")
        .addInterceptors(new HttpSessionHandshakeInterceptor());
  }

  @Bean
  public WebSocketHandler myHandler() {
    return new CableHandler();
  }

}
