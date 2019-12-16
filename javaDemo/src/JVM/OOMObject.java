package JVM;

import java.util.ArrayList;
import java.util.List;

// OOM测试类:
// 启动时限制内存大小为20m, 不可扩展(将堆的最小值-Xms与最大值-Xmx设置为一样即可避免内存溢出时自动扩展),
// -XX:+HeapDumpOnOutOfMemoryError可以让虚拟机出现内存溢出异常时Dump出当前的内存堆转储快照以便进行分析:
// -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
public class OOMObject {
    public OOMObject() {
    }

    public static void main(String[] args) {

        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
