package net.kevinztw.storage.storage;

import com.google.protobuf.ByteString;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class KeyIndexManager {
  private final ConcurrentHashMap<ByteString, KeyIndex> keyIndexMap = new ConcurrentHashMap<>();

  public void initialize() {
    Thread gc =
        new Thread(
            () -> {
              while (true) {}
            });
  }

  public Version addVersion(byte[] key) {
    KeyIndex index = getKeyIndex(key);
    return index.addVersion(Instant.now());
  }

  private KeyIndex getKeyIndex(byte[] key) {
    ByteString bk = ByteString.copyFrom(key);
    keyIndexMap.putIfAbsent(bk, new KeyIndex(key));
    return keyIndexMap.get(bk);
  }

  public Version getLatestVersion(byte[] key) {
    Instant now = Instant.now();
    KeyIndex index = getKeyIndex(key);
    return index.getLatestVersion(now);
  }

  public List<Version> getAllVersions(byte[] key) {
    Instant now = Instant.now();
    KeyIndex index = getKeyIndex(key);
    return index.getAllVersions(now);
  }

  public void remove(byte[] key) {
    KeyIndex index = getKeyIndex(key);
    index.remove();
  }
}
