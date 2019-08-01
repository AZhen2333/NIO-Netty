package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 网络服务器端程序
 */
public class NIOServer {
    public static void main(String[] args) {
        try {
            // 得到一个ServerSocketChannel对象
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 得到一个选择器Selector对象，
            Selector selector = Selector.open();
            // 绑定一个端口
            serverSocketChannel.bind(new InetSocketAddress(9999));
            // 设置成非阻塞方式
            serverSocketChannel.configureBlocking(false);
            // 把ServerSocketChannl对象注册给Selector对象
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 运作
            while (true) {
                // 监控客户端
                if (selector.select(2000) == 0) { // nio 非阻塞式的优势
                    System.out.println("Server:没有客户端搭理我， 我就干点别的事");
                    continue;
                }
                // 得到SelectionKey，判断通道里的事件
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //客户端连接请求事件
                    if (key.isAcceptable()) {
                        System.out.println("OP_ACCEPT");
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }
                    // 拉取客户端数据事件
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        channel.read(buffer);
                        System.out.println("客户端发来数据： " + new String(buffer.array()));
                    }
                    // 手动从集合中移除当前 key,防止重复处理
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
