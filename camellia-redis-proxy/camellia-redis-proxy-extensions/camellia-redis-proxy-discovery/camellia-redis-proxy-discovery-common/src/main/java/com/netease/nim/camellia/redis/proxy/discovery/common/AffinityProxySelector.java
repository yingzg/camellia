package com.netease.nim.camellia.redis.proxy.discovery.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AffinityProxySelector implements IProxySelector {

    private static final Logger logger = LoggerFactory.getLogger(AffinityProxySelector.class);

    private final ConcurrentLinkedQueue<Proxy> dynamicProxyList= new ConcurrentLinkedQueue<>();

    @Override
    public Proxy next() {
        //双向循环队列，从头上拿出来，再塞回到队尾
        Proxy ret = dynamicProxyList.poll();
        if (ret == null) return null;
        dynamicProxyList.offer(ret);
        return ret;
    }

    @Override
    public Proxy next(Boolean affinity) {
        //如果保持亲和性，则一直使用同一个proxy
        if (Boolean.TRUE.equals(affinity)) {
            Proxy head = dynamicProxyList.peek();
            if(head != null){
                return head;
            }
        }
        return next();
    }

    @Override
    public void ban(Proxy proxy) {
        logger.warn("proxy {}:{} was baned",proxy.getHost(),proxy.getPort());
        dynamicProxyList.remove(proxy);
    }

    @Override
    public void add(Proxy proxy) {
        if (!dynamicProxyList.contains(proxy)) {
            dynamicProxyList.add(proxy);
        }
    }

    @Override
    public void remove(Proxy proxy) {
        if (dynamicProxyList.size() == 1) {
            if(logger.isWarnEnabled()) {
                logger.warn("proxySet.size = 1, skip remove proxy! proxy = {}", proxy);
            }
        } else {
            dynamicProxyList.remove(proxy);
        }
    }

    @Override
    public Set<Proxy> getAll() {
        return new HashSet<>(dynamicProxyList);
    }

    @Override
    public List<Proxy> sort(List<Proxy> list) {
        List<Proxy> ret = new ArrayList<>(list);
        Collections.shuffle(ret);
        return ret;
    }
}
