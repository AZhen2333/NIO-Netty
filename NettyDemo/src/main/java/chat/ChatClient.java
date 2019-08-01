package chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @description: 聊天程序客户端
 * @author: Czz
 * @create: 2019-08-01 14:17
 */
public class ChatClient {

    /**
     * 服务器端 IP 地址
     */
    private String host;

    /**
     * 服务器端端口号
     */
    private int port;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 得到Pipeline链
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 加入编码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 加入解码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 加入业务处理对象
                            pipeline.addLast("handler", new ChatClientHandler());
                        }
                    });
            Channel channel = b.connect(host, port).sync().channel();
            System.out.println("--------" + channel.localAddress().toString().substring(1) + "--------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatClient("127.0.0.1", 9999).run();
    }
}
