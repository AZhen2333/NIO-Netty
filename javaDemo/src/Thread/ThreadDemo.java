package Thread;

public class ThreadDemo {
    public static void main(String[] args) {
        ThreadFor1 threadFor1 = new ThreadFor1();
        threadFor1.setName("线程A");
        ThreadFor2 threadFor2 = new ThreadFor2();
        threadFor2.setName("线程B");
        threadFor1.start();
        threadFor2.start();

        Thread run1 = new Thread(new RunnableFor1());
        run1.setName("线程AA");
        Thread run2 = new Thread(new RunnableFor2());
        run2.setName("线程BB");
        run1.start();
        run2.start();
    }
}
