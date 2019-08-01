package Thread;

public class SaleWindow2 implements Runnable {

    private int id = 10;

    public synchronized void saleOne(){
        if (id > 0) {
            System.out.println(Thread.currentThread().getName() + "卖了编号为" + id + "的火车票");
            id--;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            saleOne();
        }
    }
}
