/**
 * Created by Cruise on 2017/4/20.
 */
public class ThreadJoin {
    public static void main(String[] args) throws Exception{
        Thread[] ths = new Thread[3];
        for(int i=0;i<3;i++){
            final int j= i;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    System.out.println("thread"+j);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            ths[i] = t;
        }


        ths[0].start();
        ths[1].start();
        ths[2].start();

        ths[0].join();
        ths[1].join();
        ths[2].join();

    }

}
