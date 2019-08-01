package Thread;

public class RunnableFor2 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 51; i++) {
            System.out.println(Thread.currentThread().getName() + ":" + i);
        }
    }
}
