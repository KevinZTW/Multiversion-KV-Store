# Intro

This is a naive implementation for the multiversion KVstore which build on top of the RocksDB

## Features

```java
  void put(byte[] key, byte[] value);

byte[] get(byte[] key);

void remove(byte[] key);

// would get all version for oldest to newest
List<byte[]> getAllVersions(byte[] key);

```

## Design

I use the append-only way to store the data in the RocksDB, and a tree-like structure is used to store the index for
those
data.

Each key would map to a `KeyIndex` on the tree, and it contains 1 to many `Generation` which contains 0 to
many `Version` that map to the actual data in the RocksDB.
A `Generation` would be closed when a `remove` operation is called.

For every `Version` it contains a `start` and `end` timestamp, this is planned to be used as MVCC concurrency control
that for each `Transaction`
it would only read the data that is visible to it and no need to lock the data.
But I am still research to make it work to ensure it could have at least snapshot isolation level.

### PUT

For `put` operation, it would first retrieve the `KeyIndex` from the tree, locks it, and then append a new `Version` to
the end of the `Generation` and also change the last `Version`'s `end` timestamp to the current time. Then release the
lock and write to the RocksDB.

### GET

For `get` operation, same it would retrieve the `KeyIndex` from the tree, and then find the latest `Version` that
the `start` timestamp is less than the current time and the `end` timestamp is greater than the current time. Then read
the data from the RocksDB.

### REMOVE

Simply close the current `Generation` (which by create a new `Generation` to the `KeyIndex`) if it contains
any `Version`  