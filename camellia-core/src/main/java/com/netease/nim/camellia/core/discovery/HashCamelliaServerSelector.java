package com.netease.nim.camellia.core.discovery;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HashCamelliaServerSelector<T> implements CamelliaServerSelector<T> {

    @Override
    public T pick(List<T> list, Object key) {
        try {
            if (list == null || list.isEmpty()) return null;
            int size = list.size();
            if (size == 1) {
                return list.get(0);
            }
            int index;
            if (key == null) {
                index = ThreadLocalRandom.current().nextInt(list.size());
            } else {
                index = Math.abs(key.hashCode() % list.size());
            }
            return list.get(index);
        } catch (Exception e) {
            return null;
        }
    }

}