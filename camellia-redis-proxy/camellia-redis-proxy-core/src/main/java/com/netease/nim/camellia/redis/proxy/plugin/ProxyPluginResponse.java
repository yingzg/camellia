package com.netease.nim.camellia.redis.proxy.plugin;

import com.netease.nim.camellia.redis.proxy.plugin.rewrite.RouteRewriteResult;
import com.netease.nim.camellia.redis.proxy.reply.ErrorReply;
import com.netease.nim.camellia.redis.proxy.reply.Reply;

/**
 * Created by caojiajun on 2022/9/13
 */
public class ProxyPluginResponse {

    public static final ProxyPluginResponse SUCCESS = new ProxyPluginResponse(true, (Reply) null);
    public static final ProxyPluginResponse DEFAULT_FAIL = new ProxyPluginResponse(false, "ERR command proxy plugin no pass");

    private final boolean pass;
    private final boolean isRedirect;
    private final Reply reply;
    private final RouteRewriteResult routeRewriteResult;//only request plugin can set this

    public ProxyPluginResponse(boolean pass, Reply reply) {
        this.pass = pass;
        this.isRedirect = false;
        this.reply = reply;
        this.routeRewriteResult = null;
    }

    public ProxyPluginResponse(boolean pass, String errorMsg) {
        this.pass = pass;
        this.isRedirect = false;
        this.reply = new ErrorReply(errorMsg);
        this.routeRewriteResult = null;
    }

    public ProxyPluginResponse(RouteRewriteResult routeRewriteResult) {
        this.pass = true;
        this.isRedirect = false;
        this.reply = null;
        this.routeRewriteResult = routeRewriteResult;
    }

    public ProxyPluginResponse(boolean pass, boolean isRedirect, RouteRewriteResult routeRewriteResult) {
        this.pass = isRedirect ? false : pass;
        this.isRedirect = isRedirect;
        this.reply = null;
        this.routeRewriteResult = routeRewriteResult;
    }

    public boolean isPass() {
        return pass;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public Reply getReply() {
        return reply;
    }

    public RouteRewriteResult getRouteRewriterResult() {
        return routeRewriteResult;
    }
}
