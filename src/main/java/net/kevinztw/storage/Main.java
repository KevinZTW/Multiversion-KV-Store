package net.kevinztw.storage;

import com.google.protobuf.ByteString;
import java.util.List;
import net.kevinztw.storage.proto.PutRequest;
import net.kevinztw.storage.storage.MultiVersionStorage;
import net.kevinztw.storage.storage.RocksDBMultiVersionStorage;

public class Main {
  public static void main(String[] args) throws Exception {
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

    byte[] value = store.get("key1".getBytes());
    System.out.println(new String(value));

    List<byte[]> values = store.getAllVersions("key1".getBytes());

    for (byte[] v : values) {
      if (v != null) {
        System.out.println(new String(v));
      }
    }

    store.close();
  }
}
