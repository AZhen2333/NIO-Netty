package JVM;

import sun.misc.Unsafe;
import java.lang.reflect.Field;

// 直接内存;
// -XX: MaxDirectMemorySize, 指定DirectMemory大小，如果不指定，默认与Java堆最大值一样;
// -Xms20M -Xmx20M -XX:MaxDirectMemorySize=10M
public class directMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        try {
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            while (true) {
                // 申请分配内存;
                unsafe.allocateMemory(_1MB);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
