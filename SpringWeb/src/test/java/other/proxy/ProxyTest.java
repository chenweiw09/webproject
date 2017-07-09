package other.proxy;

/**
 * Created by chenwei23 on 2017/7/9.
 */
public class ProxyTest {
    public static void main(String[] args) {
        HelloProxy proxy = new HelloProxy();
        proxy.say("uyyh");

        DynamicProxy dynamicProxy = new DynamicProxy(new HelloImpl());
        Hello helloProxy = dynamicProxy.getProxy();
        helloProxy.say("uyyghd");

        HelloImpl helloImpl = CGLibProxy.getInstance().getProxy(HelloImpl.class);
        helloImpl.say("sdasasas");
    }
}
