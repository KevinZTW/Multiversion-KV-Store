package net.kevinztw.storage.storage;

import java.util.List;
import net.kevinztw.storage.proto.PutRequest;

public interface MultiVersionStorage {
  // would block until store to stable storage
  void put(PutRequest request);

  byte[] get(byte[] key);

  // would get all version for oldest to newest
  List<byte[]> getAllVersions(byte[] key);

  void initialize();

  void close();
}
