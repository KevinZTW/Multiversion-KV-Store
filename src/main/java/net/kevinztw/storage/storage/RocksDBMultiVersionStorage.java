package net.kevinztw.storage.storage;

import java.util.ArrayList;
import java.util.List;
import net.kevinztw.storage.proto.PutRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocksDBMultiVersionStorage implements MultiVersionStorage {

  public static final Logger LOGGER = LoggerFactory.getLogger(RocksDBMultiVersionStorage.class);
  private final RocksDBStorage kvBackend = new RocksDBStorage();
  private final KeyIndexManager keyIndexManager = new KeyIndexManager();

  private byte[] getStorageKey(Version version) {
    return version.toIndexKey();
  }

  public void initialize() {
    try {
      kvBackend.initialize();
    } catch (Exception e) {
      LOGGER.error("Error initializing RocksDB, exception: {}", e.getMessage());
    }
  }

  public void close() {
    try {
      kvBackend.close();
    } catch (Exception e) {
      LOGGER.error("Error closing RocksDB, exception: {}", e.getMessage());
    }
  }

  @Override
  public byte[] get(byte[] key) {
    Version version = keyIndexManager.getLatestVersion(key);
    try {
      return kvBackend.get(getStorageKey(version));
    } catch (Exception e) {
      LOGGER.error("Error getting key: {}, exception: {}", key, e.getMessage());
    }
    return new byte[0];
  }

  @Override
  public List<byte[]> getAllVersions(byte[] key) {
    List<Version> versions = keyIndexManager.getAllVersions(key);
    List<byte[]> result = new ArrayList<>();

    for (Version version : versions) {
      try {
        byte[] value = kvBackend.get(getStorageKey(version));
        result.add(value);
      } catch (Exception e) {
        LOGGER.error("Error getting key: {}, exception: {}", key, e.getMessage());
      }
    }
    return result;
  }

  @Override
  public void put(PutRequest request) {
    byte[] key = request.getKey().toByteArray();
    byte[] value = request.getValue().toByteArray();
    Version version = keyIndexManager.addVersion(key);

    try {
      kvBackend.put(getStorageKey(version), value);
    } catch (Exception e) {
      LOGGER.error("Error putting key: {}, value: {}, exception: {}", key, value, e.getMessage());
    }
  }
}
