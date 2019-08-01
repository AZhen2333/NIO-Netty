package chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @description: 聊天程序服务器端
 * @author: Czz
 * @create: 2019-08-01 13:56
 */
public class ChatServer {

    /**
     * 服务器端端口号
     */
    private int port;

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        // 往Pipeline链中添加了处理字符串的编码器和解码器， 它们加入到 Pipeline 链中后会自动工作，
                        // 使得我们在服务器端读写字符串数据时更加方便（不用人工处理 ByteBuf）
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 得到pipeline连接
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 往Pipeline链中添加一个解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            // 往Pipeline链中添加一个编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            // 往Pipeline链中添加一个自定义的业务处理对象
                            pipeline.addLast("handler", new ChatServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            System.out.println("Netty Chat Server 启动......");
            ChannelFuture future = b.bind(port).sync();
            future.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("Netty Chat Server 关闭......");
        }
    }

    public static void main(String[] args) {
        try {
            new ChatServer(9999).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
