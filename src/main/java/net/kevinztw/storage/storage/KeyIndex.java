package net.kevinztw.storage.storage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class KeyIndex {
  ReadWriteLock lock = new ReentrantReadWriteLock();
  byte[] key;
  // Create new generation when the current one is removed/dead
  ArrayList<Generation> generations = new ArrayList<>();

  public KeyIndex(byte[] key) {
    this.key = key;
    generations.add(new Generation(key));
  }

  // add to the last generation
  public Version addVersion(Instant instant) {
    lock.writeLock().lock();
    Generation generation = generations.getLast();
    Version version = generation.addVersion(instant);
    lock.writeLock().unlock();
    return version;
  }

  public Version getLatestVersion(Instant now) {
    lock.readLock().lock();
    Generation generation = generations.getLast();
    Version version = generation.getLatestVersion(now);
    lock.readLock().unlock();
    return version;
  }

  public List<Version> getAllVersions(Instant now) {

    lock.readLock().lock();
    Generation generation = generations.getLast();
    List<Version> res = generation.getAllVersions(now);
    lock.readLock().unlock();
    return res;
  }

  public void remove() {
    lock.writeLock().lock();
    if (!generations.isEmpty()) {
      generations.add(new Generation(key));
    }
    lock.writeLock().unlock();
  }
}
