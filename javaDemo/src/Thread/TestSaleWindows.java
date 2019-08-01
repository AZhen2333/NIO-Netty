package Thread;

public class TestSaleWindows {
    public static void main(String[] args) {
        SaleWindow saleWindow = new SaleWindow();
        Thread t1 = new Thread(saleWindow);
        Thread t2 = new Thread(saleWindow);

        t1.setName("窗口A");
        t2.setName("窗口B");

        t1.start();
        t2.start();
    }
}
