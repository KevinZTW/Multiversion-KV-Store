package net.kevinztw.storage.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RocksDBMultiVersionStorageTest {

  public static final String ROCKS_DB_STORE_PATH = "/tmp/rocksdb";
  MultiVersionStorage store;

  @BeforeEach
  void setUp() {
    store = new RocksDBMultiVersionStorage();
    store.initialize();
  }

  @AfterEach
  void tearDown() {
    store.close();
  }

  @BeforeEach
  @AfterEach
  public void cleanEnv() {
    try {
      FileUtils.deleteDirectory(FileUtils.getFile(ROCKS_DB_STORE_PATH));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetShouldGetLatestVersion() {

    store.put("key1".getBytes(), "value_version_1".getBytes());
    store.put("key1".getBytes(), "value_version_2".getBytes());
    store.put("key1".getBytes(), "value_version_3".getBytes());
    store.remove("key1".getBytes());
    store.put("key1".getBytes(), "value_version_4".getBytes());

    byte[] result = store.get("key1".getBytes());
    byte[] expected = "value_version_4".getBytes();

    Assertions.assertArrayEquals(expected, result);
  }

  @Test
  void testGetAllVersions() {
    store.put("key1".getBytes(), "value_version_1".getBytes());
    store.put("key1".getBytes(), "value_version_2".getBytes());
    store.put("key1".getBytes(), "value_version_3".getBytes());
    store.remove("key1".getBytes());
    store.put("key1".getBytes(), "value_version_4".getBytes());
    store.put("key1".getBytes(), "value_version_5".getBytes());
    store.put("key1".getBytes(), "value_version_6".getBytes());

    List<byte[]> result = store.getAllVersions("key1".getBytes());

    List<byte[]> expected =
        List.of(
            "value_version_4".getBytes(),
            "value_version_5".getBytes(),
            "value_version_6".getBytes());

    Assertions.assertEquals(3, result.size());
    for (int i = 0; i < 3; i++) {
      Assertions.assertArrayEquals(expected.get(i), result.get(i));
    }
  }

  @Test
  void testRemove() {
    byte[] key = "key1".getBytes();
    store.remove(key);
    store.remove(key);
    Assertions.assertEquals(0, store.getAllVersions(key).size());
    store.put(key, "value_version_1".getBytes());
    store.remove(key);
    Assertions.assertEquals(0, store.getAllVersions(key).size());
    store.put(key, "value_version_2".getBytes());
    store.remove(key);
    Assertions.assertEquals(0, store.getAllVersions(key).size());
    store.put(key, "value_version_3".getBytes());
    Assertions.assertEquals(1, store.getAllVersions(key).size());
  }
}
