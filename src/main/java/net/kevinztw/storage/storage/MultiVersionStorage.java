package net.kevinztw.storage.storage;

import java.util.List;

public interface MultiVersionStorage {
  // would block until store to stable storage
  void put(byte[] key, byte[] value);

  byte[] get(byte[] key);

  void remove(byte[] key);

  // would get all version for oldest to newest
  List<byte[]> getAllVersions(byte[] key);

  void initialize();

  void close();
}
