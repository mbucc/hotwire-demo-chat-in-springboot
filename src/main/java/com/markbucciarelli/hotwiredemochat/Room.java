package com.markbucciarelli.hotwiredemochat;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
@Table(name = "rooms")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;
  Long createdAt;
  Long updatedAt;

  // For JPA
  protected Room() {
    this.id = 0L;
    this.name = "";
    this.createdAt = 0L;
    this.updatedAt = 0L;
  }

  public static String toShortDateString(Long epochMillis) {
    ZonedDateTime x = Instant.ofEpochMilli(epochMillis).atZone(ZoneId.of("US/Eastern"));
    return x.format(DateTimeFormatter.ofPattern("MM/dd@HH:mm:ss"));
  }

  public String getCreatedAtAsString() {
    return Room.toShortDateString(this.createdAt);
  }

}
