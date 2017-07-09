package other.proxy;

/**
 * Created by chenwei23 on 2017/7/9.
 */
public class HelloImpl implements Hello {
    public void say(String msg) {
        System.out.println("Hello !"+msg);
    }
}
