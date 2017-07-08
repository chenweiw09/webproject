/**
 * Created by Cruise on 2017/3/10.
 */
public class ThreadTest extends Thread {
    private boolean run = true;

    @Override
    public void run() {
        while (run) {
//            System.out.println("running");//´ËÐÐ×¢µô
        }
        System.out.println("run end");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadTest t = new ThreadTest();
        t.start();
        Thread.sleep(100);
        t.run = false;
    }
}
