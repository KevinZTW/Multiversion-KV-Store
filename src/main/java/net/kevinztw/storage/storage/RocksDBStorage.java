package net.kevinztw.storage.storage;

import java.io.File;
import java.io.IOException;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocksDBStorage {
  public static final Logger LOGGER = LoggerFactory.getLogger(RocksDBStorage.class);
  private RocksDB db;

  private RocksDB initRocksDB() throws RocksDBException {
    RocksDB.loadLibrary();
    final Options options = new Options();
    options.setCreateIfMissing(true);

    String dbPath = "/tmp/rocksdb";
    File dbDir = new File(dbPath, "instance");
    try {
      if (!dbDir.exists() && !dbDir.mkdirs()) {
        throw new RocksDBException(
            String.format("Can't create RocksDB path '%s'", dbDir.getAbsolutePath()));
      }
      LOGGER.info("Rocksdb storage directory:{}", dbDir);
      return RocksDB.open(options, dbDir.getAbsolutePath());
    } catch (RocksDBException ex) {
      LOGGER.error(
          "Error initializing RocksDB, check configurations and permissions, exception: {}, message: {}, stackTrace: {}",
          ex.getCause(),
          ex.getMessage(),
          ex.getStackTrace());
      throw ex;
    }
  }

  public void initialize() throws IOException {
    try {
      db = initRocksDB();
    } catch (RocksDBException e) {
      throw new IOException(e);
    }
  }

  public void close() {
    db.close();
  }

  protected void put(byte[] key, byte[] value) throws RocksDBException {
    db.put(key, value);
  }

  protected byte[] get(byte[] key) throws RocksDBException {
    return db.get(key);
  }
}
