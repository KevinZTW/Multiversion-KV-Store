package net.kevinztw.playground.storage;

import java.util.List;
import net.kevinztw.playground.proto.PutRequest;

public interface MultiVersionStorage {
  // block until store to stable storage
  void put(PutRequest request);

  byte[] get(byte[] key);

  List<byte[]> getAllVersions(byte[] key);

  void initialize();

  void close();
}
