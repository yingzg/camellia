package com.netease.nim.camellia.redis.proxy.upstream.kv.command.zset.utils;

import com.netease.nim.camellia.redis.proxy.reply.BulkReply;
import com.netease.nim.camellia.redis.proxy.reply.MultiBulkReply;
import com.netease.nim.camellia.redis.proxy.reply.Reply;
import com.netease.nim.camellia.redis.proxy.util.Utils;
import com.netease.nim.camellia.tools.utils.BytesKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caojiajun on 2024/5/14
 */
public class ZSetTupleUtils {

    public static MultiBulkReply toReply(List<ZSetTuple> list, boolean withScores) {
        if (list == null) {
            return MultiBulkReply.NIL_REPLY;
        }
        if (list.isEmpty()) {
            return MultiBulkReply.EMPTY;
        }
        Reply[] replies;
        if (withScores) {
            replies = new Reply[list.size()*2];
            int i = 0;
            for (ZSetTuple tuple : list) {
                replies[i] = new BulkReply(tuple.getMember().getKey());
                replies[i+1] = new BulkReply(Utils.doubleToBytes(tuple.getScore()));
                i+=2;
            }
        } else {
            replies = new Reply[list.size()];
            int i = 0;
            for (ZSetTuple tuple : list) {
                replies[i] = new BulkReply(tuple.getMember().getKey());
                i+=1;
            }
        }
        return new MultiBulkReply(replies);
    }

    public static Map<BytesKey, Double> toMap(List<ZSetTuple> list) {
        Map<BytesKey, Double> map = new HashMap<>(list.size());
        for (ZSetTuple zSetTuple : list) {
            map.put(zSetTuple.getMember(), zSetTuple.getScore());
        }
        return map;
    }

}