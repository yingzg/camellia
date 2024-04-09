package com.netease.nim.camellia.redis.proxy.kv.core.kv;

import java.util.List;

/**
 * Created by caojiajun on 2024/4/7
 */
public interface KVClient {

    void put(byte[] key, byte[] value);

    void batchPut(List<KeyValue> list);

    KeyValue get(byte[] key);

    boolean exists(byte[] key);

    List<KeyValue> batchGet(byte[]... keys);

    void delete(byte[] key);

    void batchDelete(byte[]...keys);

    List<KeyValue> scan(byte[] startKey, byte[] prefix, int limit);
}