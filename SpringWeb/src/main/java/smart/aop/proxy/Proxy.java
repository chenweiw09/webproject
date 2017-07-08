package smart.aop.proxy;

/**
 * Created by Cruise on 2017/7/6.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
