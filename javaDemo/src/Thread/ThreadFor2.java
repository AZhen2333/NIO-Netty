package Thread;

public class ThreadFor2 extends Thread {

    public void run() {
        for (int i = 0; i < 51; i++) {
            System.out.println(this.getName() + ":" + i);

        }
    }
}
