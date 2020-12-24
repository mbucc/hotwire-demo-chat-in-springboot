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
@Table(name = "rooms")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  ZonedDateTime getCreatedAtAsDate(ZoneId tz) {
    return Instant.ofEpochMilli(this.createdAt).atZone(tz);
  }
}
