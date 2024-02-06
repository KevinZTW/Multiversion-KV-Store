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
    // the end time would be modified when the next version is added
    // there should have no two versions with the same start time since we check that in the
    // beginning of the put operation
    return Bytes.concat(key, start.toString().getBytes());
  }

  public String toString() {
    return String.format("Key: %s, Start: %s, End: %s", new String(key), start, end);
  }
}
