package com.markbucciarelli.hotwiredemochat;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  Long roomId;
  String content;
  Long createdAt;
  Long updatedAt;

  // For JPA
  protected Message() {
    this.id = 0L;
    this.roomId = 0L;
    this.content = "";
    this.createdAt = 0L;
    this.updatedAt = 0L;
  }

  ZonedDateTime getCreatedAtAsDate(ZoneId tz) {
    return Instant.ofEpochMilli(this.createdAt).atZone(tz);
  }
}
