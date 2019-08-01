package Thread;

public class ThreadFor1 extends Thread {
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(this.getName() + ":" + i);
        }
    }


}
