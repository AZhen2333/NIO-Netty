package JVM;

// -Xss参数: 设置栈内存容量:
// -Xss120k
// 在HotSpot虚拟机中并不存在区分虚拟机栈和本地方法栈，因此-Xoss(设置本地方法栈大小)实际是无效的，栈容量只由-Xss参数设定;
// 在单线程下, 无论是由于栈帧太大还是虚拟机栈容量太小, 当内存无法分配时, 虚拟机抛出的都是: java.lang.StackOverflowError而不是java.lang.OutOfMemoryError;
public class JavaVMStackSOP {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOP oom = new JavaVMStackSOP();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }

    }
}
