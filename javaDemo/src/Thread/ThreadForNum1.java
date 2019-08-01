package Thread;

public class ThreadForNum1 extends Thread {
    public void run(){
        for (int i = 0; i < 10; i++) {
            synchronized (MyLock.o){
                System.out.println(1);
                // 唤醒另一个线程
                MyLock.o.notify();
                // 等待并释放锁
                try{
                    MyLock.o.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
