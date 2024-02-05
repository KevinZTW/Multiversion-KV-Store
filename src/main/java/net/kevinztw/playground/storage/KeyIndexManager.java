package net.kevinztw.playground.storage;

import com.google.protobuf.ByteString;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import net.kevinztw.playground.proto.Version;

public class KeyIndexManager {
  private final ConcurrentHashMap<ByteString, KeyIndex> keyIndexMap = new ConcurrentHashMap<>();

  private final Queue<Version> completeVersionQueue = new ArrayDeque<>();

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
    KeyIndex index = getKeyIndex(key);
    return index.getLatestVersion();
  }

  public void complete(Version version) {
    completeVersionQueue.add(version);
  }

  public List<Version> getAllVersions(byte[] key) {
    KeyIndex index = getKeyIndex(key);
    return index.getAllVersions();
  }
}
