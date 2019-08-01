package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 网络客户端程序
 */
public class NIOClient {
    public static void main(String[] args) {
        try {
            // 得到一个网路通道
            SocketChannel socketChannel = SocketChannel.open();
            // 设置非阻塞形式
            socketChannel.configureBlocking(false);
            // 提供服务器端的IP和端口
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 9999);
            // 连接服务器端
            if (!socketChannel.connect(socketAddress)) {
                while (!socketChannel.finishConnect()) {
                    System.out.println("Client:连接服务器端的同时， 我还可以干别的一些事情");
                }
            }
            // 得到一个缓冲区并存入数据
            String msg = "hello";
            ByteBuffer writeBuf = ByteBuffer.wrap(msg.getBytes());
            // 发送数据
            socketChannel.write(writeBuf);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
