package net.kevinztw.playground.storage;

import com.google.protobuf.Timestamp;
import java.time.Instant;

public class TimeUtils {
  public static Timestamp toTimeStamp(Instant instant) {
    return Timestamp.newBuilder()
        .setSeconds(instant.getEpochSecond())
        .setNanos(instant.getNano())
        .build();
  }
}
