package net.kevinztw.storage.storage;

import java.util.List;
import net.kevinztw.storage.proto.PutRequest;

public interface MultiVersionStorage {
  // block until store to stable storage
  void put(PutRequest request);

  byte[] get(byte[] key);

  List<byte[]> getAllVersions(byte[] key);

  void initialize();

  void close();
}
