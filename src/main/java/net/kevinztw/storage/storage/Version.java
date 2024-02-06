package net.kevinztw.storage.storage;

import com.google.common.primitives.Bytes;
import java.time.Instant;

public class Version {
  byte[] key;
  Instant start;
  Instant end;

  public Version(byte[] key, Instant instant) {
    this.key = key;
    this.start = instant;
    this.end = Instant.MAX;
  }

  public byte[] toIndexKey() {
    return Bytes.concat(key, start.toString().getBytes(), end.toString().getBytes());
  }
}
