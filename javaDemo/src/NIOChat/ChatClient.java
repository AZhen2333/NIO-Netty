package NIOChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 聊天程序的客户端：
 * 向服务器端发送数据， 并能接收服务器广播的数据
 */
public class ChatClient {

    private final String HOST = "127.0.0.1"; //服务器地址
    private int PORT = 9999; //服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public ChatClient() {
        try {
            // 得到选择器
            selector = Selector.open();
            // 连接远程服务器
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", PORT));
            // 设置非阻塞
            socketChannel.configureBlocking(false);
            // 注册选择器并设置为 read
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println("---------------Client(" + userName + ") is ready---------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器端发送数据
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        if (msg.equalsIgnoreCase("byte")) {
            try {
                socketChannel.close();
                socketChannel = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        msg = userName + "说: " + msg;
        try {
            // 往通道中写数据
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMsg() {
        try {
            int readChannels = selector.select();
            // 有可用通道
            if (readChannels > 0) {
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey sk = (SelectionKey) iterator.next();
                    if (sk.isReadable()) {
                        // 得到关联的通道
                        SocketChannel sc = (SocketChannel) sk.channel();
                        // 得到一个缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(2014);
                        // 读取数据并存储到缓冲区
                        sc.read(buffer);
                        // 把缓冲区的数据转化为字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    // 删除当前 SelectionKey， 防止重复处理
                    iterator.remove();
                }
            } else {
                System.out.println("人呢？ 都去哪儿了？ 没人聊天啊...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
