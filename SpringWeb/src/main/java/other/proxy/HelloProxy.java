package other.proxy;

/**
 * Created by chenwei23 on 2017/7/9.
 *
 * 静态代理
 */
public class HelloProxy implements Hello {
    private HelloImpl helloImpl;

    public HelloProxy(){
        helloImpl = new HelloImpl();
    }

    public void say(String msg) {
        before();
        helloImpl.say(msg);
        after();
    }

    private void before() {
        System.out.println("Before");
    }

    private void after() {
        System.out.println("After");
    }
}
