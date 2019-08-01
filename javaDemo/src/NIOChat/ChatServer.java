package NIOChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * 聊天程序的服务器端：
 * 可以接受客户端发来的数据， 并能把数据广播给所有客户端
 */
public class ChatServer {

    private Selector selector;

    private ServerSocketChannel listenerChannel;
    // 服务端口
    private static final int PORT = 9999;

    public ChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // 打开监听通道
            listenerChannel = ServerSocketChannel.open();
            // 绑定端端口
            listenerChannel.bind(new InetSocketAddress(PORT));
            // 设置成非阻塞型
            listenerChannel.configureBlocking(false);
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);// OP_ACCEPT:接收连接继续事件，表示服务器监听到了客户连接，服务器可以接收这个连接了
            printInfo("Chat Server is ready.......");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            // 不停轮询
            while (true) {
                // 获取就绪channle
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenerChannel.accept();
                            // 非阻塞模式
                            sc.configureBlocking(false);
                            // 注册到选择器上并监听read
                            sc.register(selector, SelectionKey.OP_READ); // OP_READ:读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
                            System.out.println(sc.getRemoteAddress().toString().substring(1) + "上线了...");
                            //将此对应的 channel 设置为 accept,接着准备接受其他客户端请求
                            key.interestOps(SelectionKey.OP_ACCEPT);
                        }
                        // 监听到read
                        if (key.isReadable()) {
                            //读取客户端发来的数据
                            readMsg(key);
                        }
                        // 一定要把当前key删掉， 防止重复处理
                        iterator.remove();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取客户端发来的数据
     *
     * @param key
     */
    private void readMsg(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // 得到关联的通道
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 从通道中读取数据并存储到缓冲区中
            int count = channel.read(buffer);
            if (count > 0) {
                // 把缓冲区的数据转化为字符串
                String msg = new String(buffer.array());
                printInfo(msg);
                // 将关联的channel设置为read，继续准备接受数据
                key.interestOps(SelectionKey.OP_READ);
                BroadCast(channel, msg); //向所有客户端广播数据
            }
            buffer.clear();
        } catch (IOException e) {
            try {
                //当客户端关闭 channel 时， 进行异常如理
                printInfo(channel.getRemoteAddress().toString().substring(1) + "下线了...");
                key.cancel(); //取消注册
                channel.close(); //关闭通道

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void BroadCast(SocketChannel except, String msg) throws IOException {
        System.out.println("发送广播...");
        // 广播数据到所有的 SocketChannel 中
        for (SelectionKey key : selector.keys()) {
            Channel targetchannel = key.channel();
            // 排除自身
            if (targetchannel instanceof SocketChannel && targetchannel != except) {
                SocketChannel dest = (SocketChannel) targetchannel;
                // 把数据存到缓存区中
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 往通道中写数据
                dest.write(buffer);
            }

        }

    }

    private void printInfo(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("[" + dateFormat.format(new Date()) + "] -> " + str);

    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}
