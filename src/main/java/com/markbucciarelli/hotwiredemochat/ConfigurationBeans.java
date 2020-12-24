package com.markbucciarelli.hotwiredemochat;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBeans {

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }

}
