package net.kevinztw.playground.storage;

import com.google.protobuf.ByteString;
import java.time.Instant;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.kevinztw.playground.proto.Version;
import net.kevinztw.playground.storage.exceptions.DuplicateVersionException;

public class KeyIndex {
  ReadWriteLock lock = new ReentrantReadWriteLock();
  byte[] key;
  TreeMap<Instant, Version> versionMap = new TreeMap<>();

  public KeyIndex(byte[] key) {
    this.key = key;
  }

  public Version addVersion(Instant instant) {
    lock.writeLock().lock();
    if (versionMap.containsKey(instant)) {
      lock.writeLock().unlock();
      throw new DuplicateVersionException(
          String.format("Version: %s for key: %s already exists", instant, this.key));
    }

    Version version =
        Version.newBuilder()
            .setKey(ByteString.copyFrom(this.key))
            .setVal(TimeUtils.toTimeStamp(instant))
            .build();
    versionMap.put(instant, version);
    lock.writeLock().unlock();
    return version;
  }

  public Version getLatestVersion() {
    return versionMap.lastEntry().getValue();
  }

  public List<Version> getAllVersions() {
    return List.copyOf(versionMap.values());
  }
}
