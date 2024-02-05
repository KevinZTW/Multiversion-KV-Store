package net.kevinztw.playground.storage;

import com.google.common.primitives.Bytes;
import java.util.ArrayList;
import java.util.List;
import net.kevinztw.playground.proto.PutRequest;
import net.kevinztw.playground.proto.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocksDBMultiVersionStorage implements MultiVersionStorage {

  public static final Logger LOGGER = LoggerFactory.getLogger(RocksDBMultiVersionStorage.class);
  private KeyIndexManager keyIndexManager = new KeyIndexManager();
  private RocksDBStorage kvBackend = new RocksDBStorage();

  private byte[] getStorageKey(Version version) {
    return Bytes.concat(version.getKey().toByteArray(), version.getVal().toByteArray());
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
      byte[] value = kvBackend.get(getStorageKey(version));
      return value;
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
    keyIndexManager.complete(version);

    try {
      kvBackend.put(getStorageKey(version), value);
    } catch (Exception e) {
      LOGGER.error("Error putting key: {}, value: {}, exception: {}", key, value, e.getMessage());
    }
  }
}
