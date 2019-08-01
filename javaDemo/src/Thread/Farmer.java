package Thread;

public class Farmer extends Thread {
    public void run() {
        while (true) {
            synchronized (Kuang.kuang) {
                // 当框满了10，就停下
                if (Kuang.kuang.size() == 10) {
                    try {
                        // 等待并释放锁
                        Kuang.kuang.wait();
                        System.out.println("篮子满了.....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 放入
                Kuang.kuang.add("apple");
                System.out.println("农夫网筐中放了有" + Kuang.kuang.size() + "个水果");
                // 唤醒另一个线程
                Kuang.kuang.notify();
            }
            // 模拟速度控制
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
