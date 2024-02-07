package net.kevinztw.storage.storage;

import static org.junit.jupiter.api.Assertions.*;

import com.google.protobuf.ByteString;
import java.util.List;
import net.kevinztw.storage.proto.PutRequest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RocksDBMultiVersionStorageTest {

  public static final String ROCKS_DB_STORE_PATH = "/tmp/rocksdb";

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
  void testget() {}

  @Test
  void testGetAllVersions() {
    MultiVersionStorage store = new RocksDBMultiVersionStorage();
    store.initialize();

    PutRequest request1 =
        PutRequest.newBuilder()
            .setKey(ByteString.copyFrom("key1".getBytes()))
            .setValue(ByteString.copyFrom("value_version_1".getBytes()))
            .build();

    PutRequest request2 =
        PutRequest.newBuilder()
            .setKey(ByteString.copyFrom("key1".getBytes()))
            .setValue(ByteString.copyFrom("value_version_2".getBytes()))
            .build();

    PutRequest request3 =
        PutRequest.newBuilder()
            .setKey(ByteString.copyFrom("key2".getBytes()))
            .setValue(ByteString.copyFrom("value_version_1".getBytes()))
            .build();

    PutRequest request4 =
        PutRequest.newBuilder()
            .setKey(ByteString.copyFrom("key1".getBytes()))
            .setValue(ByteString.copyFrom("value_version_3".getBytes()))
            .build();

    store.put(request1);
    store.put(request2);
    store.put(request3);
    store.put(request4);

    List<byte[]> result = store.getAllVersions("key1".getBytes());

    List<byte[]> expected =
        List.of(
            "value_version_1".getBytes(),
            "value_version_2".getBytes(),
            "value_version_3".getBytes());

    Assertions.assertEquals(3, result.size());
    for (int i = 0; i < 3; i++) {
      Assertions.assertArrayEquals(expected.get(i), result.get(i));
    }

    store.close();
  }

  @Test
  void put() {}
}
