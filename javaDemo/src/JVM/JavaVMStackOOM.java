package JVM;

// -Xss2M
// 多线程导致内存溢出, 在不减少线程数的情况下, 只能通过减少最大堆或者减少栈容量来换取更多的线程;
// 此代码会导致操作系统假死;
public class JavaVMStackOOM {
    public void dontstop() {
        while (true) {
        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontstop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
