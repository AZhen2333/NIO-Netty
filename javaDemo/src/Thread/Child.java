package Thread;

public class Child extends Thread {
    public void run() {
        while (true) {
            synchronized (Kuang.kuang) {
                // 没有水果，就停下
                if (Kuang.kuang.size() == 0) {
                    try {
                        // 等待并释放锁
                        Kuang.kuang.wait();
                        System.out.println("篮子空了~~~");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 吃水果
                Kuang.kuang.remove("apple");
                System.out.println("小孩吃吃了一个水果，筐中剩下" + Kuang.kuang.size() + "个水果");
                // 模拟控制速度
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
