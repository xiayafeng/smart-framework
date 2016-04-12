package org.smart4j.framework.proxy;

/**
 * 代理接口
 */
public interface Proxy {
    /**
     * 链式调用
     */
    Object doProxy(ProxyChain proxyChain ) throws Throwable;
}
