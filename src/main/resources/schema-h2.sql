-- You chat about a particular topic in a room.
CREATE TABLE IF NOT EXISTS rooms (
  id         IDENTITY      NOT NULL PRIMARY KEY,
  name       VARCHAR(100)  NOT NULL,
  created_at BIGINT        NOT NULL,
  updated_at BIGINT        NOT NULL
);

-- A room has many messages.
CREATE TABLE IF NOT EXISTS messages (
  id         IDENTITY      NOT NULL PRIMARY KEY,
  room_id    INT           NOT NULL,
  content    VARCHAR(500)  NOT NULL,
  created_at BIGINT        NOT NULL,
  updated_at BIGINT        NOT NULL,

  FOREIGN KEY (room_id) REFERENCES rooms(id)
)
;
