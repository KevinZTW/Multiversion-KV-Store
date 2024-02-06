package net.kevinztw.storage.storage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.kevinztw.storage.storage.exceptions.DuplicateVersionException;

public class Generation {
  ReadWriteLock lock = new ReentrantReadWriteLock();
  TreeMap<Instant, Version> versionMap = new TreeMap<>();
  byte[] key;

  public Generation(byte[] key) {
    this.key = key;
  }

  public Version addVersion(Instant instant) {
    lock.writeLock().lock();
    if (versionMap.containsKey(instant)) {
      lock.writeLock().unlock();
      throw new DuplicateVersionException(
          String.format("Version: %s for key: %s already exists", instant, this.key));
    }

    Version version = new Version(this.key, instant);

    if (!versionMap.isEmpty()) {
      Version lastVersion = versionMap.lastEntry().getValue();
      lastVersion.end = instant;
    }
    versionMap.put(instant, version);
    lock.writeLock().unlock();
    return version;
  }

  public Version getLatestVersion(Instant now) {
    lock.readLock().lock();
    // find the "first" entry for it's start < now < end
    for (Version version : versionMap.values()) {
      if (version.start.isBefore(now) && version.end.isAfter(now)) {
        lock.readLock().unlock();
        return version;
      }
    }

    lock.readLock().unlock();
    return null;
  }

  public List<Version> getAllVersions(Instant now) {
    List<Version> res = new ArrayList<>();
    lock.readLock().lock();
    for (Version version : versionMap.values()) {
      if (version.end.isBefore(now) || (version.start.isBefore(now) && version.end.isAfter(now))) {
        res.add(version);
      }
    }
    lock.readLock().unlock();
    return res;
  }
}
