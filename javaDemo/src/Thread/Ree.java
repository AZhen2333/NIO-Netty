package Thread;

import java.util.concurrent.locks.ReentrantLock;

public class Ree {
    ReentrantLock lock;

    Ree(ReentrantLock lock) {
        this.lock = lock;
    }

    private Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (lock.tryLock()) {
                            try {
                                System.out.println("Locked:" + Thread.currentThread().getName());
                                Thread.sleep(800);
                            } finally {
                                lock.unlock();
                                System.out.println("UnLocked:" + Thread.currentThread().getName());
                            }
                            System.out.println("break before");
                            break;
                        } else {
                            //System.out.println("Unable to lock " + Thread.currentThread().getName());
                        }
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread() + " is Interupted");
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Ree test = new Ree(lock);
        Ree test2 = new Ree(lock);
        Thread thread1 = new Thread(test.getRunnable(), "firstThread");
        Thread thread2 = new Thread(test2.getRunnable(), "secondThread");
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("interupt begin");
        thread2.interrupt();
        System.out.println("interupt end");
    }
}
